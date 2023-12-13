import { useLogin, useUser } from "../UserContext";
import { navigate } from "@reach/router";
import CollectionFilter from "./CollectionFilter";
import {
	Button, ButtonGroup, Card, CardBody, CardFooter, CardHeader, Container,
	Dropdown,
	DropdownItem,
	DropdownMenu, DropdownToggle,
	Form,
	Modal,
	ModalBody,
	ModalFooter,
	ModalHeader, Popover, PopoverBody, PopoverHeader,
	Label
} from "reactstrap";
import { useEffect, useState } from "react";
import CollectionSearchBar from "./CollectionSearchBar";
import CreateComicModal from "./CreateComicModal";
import dropdown from "bootstrap/js/src/dropdown";

const url = "http://localhost:8080"

const MyCollection = () => {
	const [loginStatus] = useLogin()
	const [currentUser] = useUser()
	const [collection, setCollection] = useState([])

	const [grade, setGrade] = useState(0)

	const [toggleModal, setToggleModal] = useState(false)

	const [isGraded, setIsGraded] = useState(false)
	const [isSlabbed, setIsSlabbed] = useState(false)
	const [isSigned, setIsSigned] = useState(false)
	const [isAuthenticated, setIsAuthenticated] = useState(false)

	const [gradeDropdownOpen, setGradeDropdownOpen] = useState(false)
	const [slabDropdownOpen, setSlabDropdownOpen] = useState(false)
	const [signedDropdownOpen, setSignedDropdownOpen] = useState(false)
	const [authenticatedDropdownOpen, setAuthenticatedDropdownOpen] = useState(false)

	const [createModalOpen, setCreateModalOpen] = useState(false)

	const [popoverStates, setPopoverStates] = useState(Array(collection.length).fill(false))

	const [updateComicForm, setUpdateComicForm] = useState({ // Used for comic editing
		publisher: "",
		series: "",
		volume: "",
		issue: "",
		publicationDate: "",
		comicTitle: "",
		creators: [],
		principleCharacters: [],
		description: "",
		value: "",
	})

	const [oldComic, setOldComic] = useState({
		publisher: "",
		series: "",
		volume: "",
		issue: "",
		publicationDate: "",
		comicTitle: "",
		creators: [],
		principleCharacters: [],
		description: "",
		value: "",
	})

	const openPopover = (index) => {
		const newStates = [...popoverStates]
		newStates[index] = !newStates[index]
		setPopoverStates(newStates)
	}

	const openCreateModal = (isOpen) => {
		setCreateModalOpen(isOpen)
	}

	// Grading
	const handleGrade = (isGraded, comic, grade) => {
		setIsGraded(isGraded)
		setGrade(grade)
		fetchGrade(currentUser, comic, grade)
	}

	const fetchGrade = (name, comic, grade) => {
		console.log(grade);
		const json = JSON.stringify({
			comic: comic,
			grade: grade
		})
		fetch(`${url}/collection/grade?name=${name}`, {
			method: "PUT",
			body: (json),
			headers: {
				'Content-type': 'application/json; charset=UTF-8'
			}
		}).then((response) => {
			return response.json();
		}).then((data) => {
			console.log(data);
		}).catch((error) => {
			console.log("Error grading comic: ", error);
		});
	}

	// Slabbing
	const handleSlab = (isSlabbed) => {
		setIsSlabbed(isSlabbed)
	}

	// Signing
	const handleSignatures = (isSigned) => {
		setIsSigned(isSigned)
	}

	const handleAuthentication = (isAuthenticated) => {
		setIsAuthenticated(isAuthenticated)
		console.log(isAuthenticated)
	}

	// Editting Modal
	const openModal = (isOpen, comic) => {
		updateComicForm.publisher = comic.publisher
		updateComicForm.series = comic.series
		updateComicForm.volume = comic.volume
		updateComicForm.issue = comic.issue
		updateComicForm.publicationDate = comic.publicationDate
		updateComicForm.comicTitle = comic.comicTitle
		updateComicForm.creators = comic.creators
		updateComicForm.principleCharacters = comic.principleCharacters
		updateComicForm.description = comic.description
		updateComicForm.value = comic.value
		
		oldComic.publisher = comic.publisher
		oldComic.series = comic.series
		oldComic.volume = comic.volume
		oldComic.issue = comic.issue
		oldComic.publicationDate = comic.publicationDate
		oldComic.comicTitle = comic.comicTitle
		oldComic.creators = comic.creators
		oldComic.principleCharacters = comic.principleCharacters
		oldComic.description = comic.description
		oldComic.value = comic.value
		console.log(isAuthenticated)
		setToggleModal(isOpen)
	}

	const toggleGradeDropdown = () => {
		setGradeDropdownOpen((prevState) => !prevState);
	};

	const toggleSlabDropdown = () => {
		setSlabDropdownOpen((prevState) => !prevState);
	};

	const toggleSignedDropdown = () => {
		setSignedDropdownOpen((prevState) => !prevState);
	};

	const toggleAuthenticatedDropdown = () => {
		setAuthenticatedDropdownOpen((prevState) => !prevState);
	};


	useEffect(() => {
		const fetchData = async () => {
			try {
				await fetchCollection(currentUser)
			} catch (error) {
				console.log(error)
			}
		}
		if (currentUser) {
			fetchData()
		}
	}, [currentUser])

	const arrayToStrings = (array) => {
		return array.join(', ')
	}

	const fetchCollection = (username) => {
		fetch(`${url}/collection/?name=${username}`)
			.then((response) => (
				response.json()
					.then((data) => {
						console.log("Fetched Data: ", data)
						setCollection(data.comics)
					}).catch((error) => {
						console.log('Error fetching collection: ', error)
					})
			))
	}

	const removeComic = async (comic) =>{
		console.log(currentUser)
		console.log(comic);
		console.log(JSON.stringify(comic))
		fetch(`${url}/collection/remove?name=${currentUser}`, {
			method:"DELETE" ,
			body: JSON.stringify(comic),
			headers : {
				'Content-Type': 'application/json; charset=UTF-8'
			}
		})
			.then((response) => (
				response.json()
					.then((data) => {
						fetchCollection(currentUser)
						console.log("Removed: ", data)
					}).catch((error) => {
						console.log('Error removing comic: ', error)
				})
			))

	}
  
	// Editting
	const handleInput = (event) => {
		const { name, value } = event.target;
		setUpdateComicForm((prevState) => ({
			...prevState,
			[name]: value,
		}));
		console.log(updateComicForm)
	}

	const putComic = (oldComic, publisher, series, volume, issue,
		publicationDate, comicTitle, creators,
		principleCharacters, description, value) => {
		let creatorList = creators
		console.log(creators, typeof creators)
		if (creators !== undefined && typeof creators === "string") {
			creatorList = creators.split(', ')
			console.log("split")
		}
		console.log(creatorList);
		let principleCharactersList = principleCharacters
		if (principleCharacters !== undefined && typeof principleCharacters === "string") {
			principleCharactersList = creators.split(', ')
		}
		console.log(principleCharactersList);
		const valueDouble = parseFloat(value);
		console.log(publicationDate, typeof publicationDate)
		const comic = {
			comicID: -1,
			publisher: publisher,
			series: series,
			volume: volume,
			issue: issue,
			publicationDate: publicationDate,
			comicTitle: comicTitle,
			creators: creatorList,
			principleCharacters: principleCharactersList,
			description: description,
			value: valueDouble,
		}
		const json = JSON.stringify({ "oldComic": oldComic, "newComic": comic })
		console.log(json)
		fetch(`${url}/collection/update?name=${currentUser}`, {
			method: "PUT",
			body: json,
			headers: {
				'Content-type': 'application/json; charset=UTF-8'
			}
		}).then((response) => {
			fetchCollection(currentUser)
			return response.json();
		}).then((data) => {
			console.log(data);
		}).catch((error) => {
			console.log("Error adding comic: ", error);
		});
	}

	const handleSubmit = (event) => {
		console.log("Form submitted")
		event.preventDefault()
		setToggleModal(false)
		putComic(
			oldComic,
			updateComicForm.publisher,
			updateComicForm.series,
			updateComicForm.volume,
			updateComicForm.issue,
			updateComicForm.publicationDate,
			updateComicForm.comicTitle,
			updateComicForm.creators,
			updateComicForm.principleCharacters,
			updateComicForm.description,
			updateComicForm.value
		)
	}

	const undoAction = (username) => {
		fetch(`${url}/collection/undo?name=${username}`, {
			method:"PUT"
		})
			.then((response) => {
				fetchCollection(username)
				return response.json();
			}).then((data) => {
				console.log(data);
			}).catch((error) => {
				console.log("Error undoing action: ", error);
			});
	}

	const redoAction = (username) => {
		fetch(`${url}/collection/redo?name=${username}`, {
			method:"PUT"
		})
			.then((response) => {
				fetchCollection(username)
				return response.json();
			}).then((data) => {
				console.log(data);
			}).catch((error) => {
				console.log("Error undoing action: ", error);
			});
	}
 

	return (
		<Container>
			<h1 className={'Poppins-Bold text-white mb-5'}> My Collection </h1>

			<Container className={'d-flex flex-row align-items-center'}>
				<button onClick={() => openCreateModal(true)} className={'search_button Poppins-Bold text-white box_shadow bg-gradient Poppins w-25'}> Create Comic </button>
				<CollectionSearchBar
					comics={collection}
					setComics={setCollection}
				/>
				<button onClick={() => undoAction(currentUser)} className={'search_button Poppins-Bold text-white box_shadow bg-gradient Poppins w-25 m-3'}> Undo </button>
               	<button onClick={() => redoAction(currentUser)} className={'search_button Poppins-Bold text-white box_shadow bg-gradient Poppins w-25 m-3'}> Redo </button>

			</Container>

			<hr style={{
				height: '2px',
				borderWidth: '0',
				color: 'white',
				backgroundColor: 'white'
			}} />
			<div className={"w-100 comicGrid"}>
				{collection.map((item, index) => (
					<Card key={item.key} color={'secondary bg-gradient'} className={"comicCard overflow-visible box_shadow"}>
						<CardHeader className={'text-white Poppins-Bold h-25 d-flex flex-row justify-content-center align-items-center'}>
							{item.comicTitle}
						</CardHeader>
						<div className={'mb-5'}>
							<CardBody className={'h-75 d-flex flex-column justify-content-center align-content-lg-start'}>
								<p className={"Poppins-Bold m-0 text-white"}> Publisher: {item.publisher}</p>
								<p className={"Poppins-Bold m-0 text-white"}> Series: {item.series}</p>
								<p className={"Poppins-Bold m-0 text-white"}> Volume #: {item.volume}</p>
								<p className={"Poppins-Bold m-0 text-white"}> Issue #: {item.issue}</p>
								{isGraded && <p className={"Poppins-Bold m-0"}> Grade: {grade}</p>}
								{isSlabbed && <p className={"Poppins-Bold m-0"}> Slabbed: Yes </p>}
								{isSigned && <p className={"Poppins-Bold m-0"}> Signed: Yes</p>}
								{isAuthenticated && <p className={"Poppins-Bold m-0"}> Authenticated: Yes </p>}
							</CardBody>
							<CardFooter className={'h-25 d-flex flex-column align-content-lg-start'}>
								<Button id={`popover${index}`} type="button" onClick={() => openPopover(index)} color="box_shadow bg-gradient" className={"moreInfoButton text-white Poppins-Bold"}> More Info </Button>
								<Popover flip target={`popover${index}`} isOpen={popoverStates[index]}>
									<PopoverHeader>
										{item.comicTitle} Info
									</PopoverHeader>
									<PopoverBody>
										<p className={"Poppins-Bold m-0"}> Publisher: {item.publisher}</p>
										<p className={"Poppins-Bold m-0"}> Series: {item.series}</p>
										<p className={"Poppins-Bold m-0"}> Volume #: {item.volume}</p>
										<p className={"Poppins-Bold m-0"}> Issue #: {item.issue}</p>
										<p className={"Poppins-Bold m-0"}> Publication Date: {item.publicationDate}</p>
										<p className={"Poppins-Bold m-0"}> Comic Title: {item.comicTitle}</p>
										<p className={"Poppins-Bold m-0"}> Creators: {arrayToStrings(item.creators)}</p>
										<p className={"Poppins-Bold m-0"}> Principle Characters: {arrayToStrings(item.principleCharacters)}</p>
										<p className={"Poppins-Bold m-0"}> Description: {item.description}</p>
										<p className={"Poppins-Bold m-0"}> Value: ${item.value}</p>
									</PopoverBody>
								</Popover>
								<ButtonGroup className={'mt-3'}>
									<Button color={' bg-gradient'} className={"box_shadow editButton text-white Poppins-Bold"} onClick={() => openModal(true, item)}>
										Edit
									</Button>
									<Button color={'danger bg-gradient'} className={"box_shadow removeButton text-white Poppins-Bold"} onClick={()=> removeComic(item)}>
										Remove
									</Button>
								</ButtonGroup>
							</CardFooter>
						</div>
						<CreateComicModal
							isOpen={createModalOpen}
							setIsOpen={setCreateModalOpen}
							onSubmit={fetchCollection}
						/>

						<Modal isOpen={toggleModal}>
							<ModalHeader>
								Edit Comic
							</ModalHeader>
							<Form onSubmit={handleSubmit}>
								<ModalBody className={'d-flex flex-column align-content-lg-start'}>
									<Label >
										Publisher:{" "}
										<input
											className={'w-75'}
											name={"publisher"}
											placeholder={'Enter a publisher name'}
											value={updateComicForm.publisher}
											onChange={handleInput}
										/>
									</Label>
									<Label>
										Series:{" "}
										<input
											name={"series"}
											value={updateComicForm.series}
											placeholder={'Enter a series name'}
											onChange={handleInput} />
									</Label>
									<Label>
										Volume #:{" "}
										<input
											name={"volume"}
											value={updateComicForm.volume}
											placeholder={'Enter a volume'}
											onChange={handleInput}
										/>
									</Label>
									<Label>
										Issue #: {" "}
										<input
											name={"issue"}
											onChange={handleInput}
											value={updateComicForm.issue}
											placeholder={"Enter an issue number"} />
									</Label>
									<Label>
										Publication Date: {" "}
										<input
											name={"publicationDate"}
											value={updateComicForm.publicationDate}
											type={"date"}
											placeholder={"Enter a date"}
											onChange={handleInput} />
									</Label>
									<Label>
										Comic Title: {" "}
										<input
											name={"comicTitle"}
											value={updateComicForm.comicTitle}
											placeholder={"Enter a title"}
											onChange={handleInput} />
									</Label>
									<Label>
										Creators: {" "}
										<input
											name={"creators"}
											type={"text"}
											value={updateComicForm.creators}
											placeholder={"Separate by ', '"}
											onChange={handleInput} />
									</Label>
									<Label>
										Principle Characters: {" "}
										<input
											name={"principleCharacters"}
											type={"text"}
											value={updateComicForm.principleCharacters}
											placeholder={"Separated by ', '"}
											onChange={handleInput} />
									</Label>
									<Label>
										Description: {" "}
										<input
											name={"description"}
											value={updateComicForm.description}
											placeholder={"Enter a description"}
											onChange={handleInput} />
									</Label>
									<Label>
										Value: {" "}
										<input
											name={"value"}
											type="number" min="0.00" step="0.01" max="1000000"
											value={updateComicForm.value}
											placeholder={"Enter the comics value"}
											onChange={handleInput} />
									</Label>
								</ModalBody>
								<ModalBody className={'d-flex flex-column'}>
									<ButtonGroup className={'m-3'}>
										<Dropdown isOpen={gradeDropdownOpen} toggle={toggleGradeDropdown} direction={'down'}>
											<DropdownToggle color={'primary'} className={'modalDropdown'} caret> Add Grade</DropdownToggle>
											<DropdownMenu>
												{[1, 2, 3, 4, 5, 6, 7, 8, 9, 10].map((grade) => (
													<DropdownItem key={grade} onClick={() => handleGrade(true, item, grade)}>
														Grade {grade}
													</DropdownItem>
												))}
											</DropdownMenu>
										</Dropdown>
										<Container className={'modalInput'}>
											{isGraded ? `Grade ${grade}` : ("No")}
										</Container>
									</ButtonGroup>
									<ButtonGroup className={'m-3'}>
										<Dropdown isOpen={slabDropdownOpen} toggle={toggleSlabDropdown} direction={'down'}>
											<DropdownToggle color={'primary'} className={'modalDropdown'} caret> Add Slabbing</DropdownToggle>
											<DropdownMenu>
												<DropdownItem onClick={() => handleSlab(true)}> Yes </DropdownItem>
												<DropdownItem onClick={() => handleSlab(false)}> No </DropdownItem>
											</DropdownMenu>
										</Dropdown>
										<Container className={'modalInput'}>
											{isSlabbed ? "Yes" : "No"}
										</Container>
									</ButtonGroup>
									<ButtonGroup className={'m-3'}>
										<Dropdown isOpen={signedDropdownOpen} toggle={toggleSignedDropdown} direction={'down'}>
											<DropdownToggle color={'primary'} className={'modalDropdown'} caret> Add Signature</DropdownToggle>
											<DropdownMenu className={'modalDropdown'}>
												<DropdownItem onClick={() => handleSignatures(true)}> Yes </DropdownItem>
												<DropdownItem onClick={() => handleSignatures(false)}> No </DropdownItem>
											</DropdownMenu>
										</Dropdown>
										<Container className={'modalInput'}>
											{isSigned ? "Yes" : "No"}
										</Container>
									</ButtonGroup>
									<ButtonGroup className={'m-3'}>
										<Dropdown isOpen={authenticatedDropdownOpen} toggle={toggleAuthenticatedDropdown} direction={'down'}>
											<DropdownToggle color={'primary bg-gradient'} className={'modalDropdown'} caret>
												Add Authentication
											</DropdownToggle>
											<DropdownMenu className={'modalDropdown'}>
												<DropdownItem onClick={() => handleAuthentication(true)}>
													Yes
												</DropdownItem>
												<DropdownItem onClick={() => handleAuthentication(false)}>
													No
												</DropdownItem>
											</DropdownMenu>
										</Dropdown>
										<Container className={'modalInput'}>
											{isAuthenticated ? "Yes" : "No"}
										</Container>
									</ButtonGroup>
								</ModalBody>
								<ModalFooter>
									<Button type={'submit'} color={'primary bg-gradient'}>
										Submit
									</Button>
									<Button onClick={() => setToggleModal(false)} color={'danger bg-gradient'}>
										Cancel
									</Button>
								</ModalFooter>
							</Form>
						</Modal>
					</Card> 
				))}
			</div>
		</Container>
	)
}

export default MyCollection;