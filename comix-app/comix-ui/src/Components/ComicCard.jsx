import {Users} from "../User";
import {useLogin, useUser} from "../UserContext";
import {useEffect, useState} from "react";
import {Card, CardBody, CardFooter, CardHeader} from "reactstrap";
const url = 'http://localhost:8080'
const ComicCard = (props) => {
	const {comicKey, publisher, series, volume, issue, cover, user} = props;
	const [toggleModal, setToggleModal] = useState(false)

	const [currentUser] = useUser()
	const [loginStatus] = useLogin()
	const [showPopup, setShowPopup] = useState(false)

	useEffect(() => {
		console.log("Log in status: ", loginStatus)
	}, [loginStatus])


	const fetchAddToCollection = (name) => {
		const comic = {
			comicID: props.comicKey,
			publisher: props.publisher,
			series: props.series,
			volume: props.volume,
			issue: props.issue,
			publicationDate: props.publicationDate,
			comicTitle: props.comicTitle,
			creators: props.creators,
			principleCharacters: props.principleCharacters,
			description: props.description,
			value: props.value,
		}
		fetch(`${url}/collection/add?name=${name}`, {
			method: "PUT",
			body: JSON.stringify(comic),
			headers: {
				'Content-type': 'application/json; charset=UTF-8'
			}
		}).then((response) => {
			return response.json();
		}).then((data) => {
			console.log(data);
		}).catch((error) => {
			console.log("Error adding comic: ", error);
		});
	}
	const addToCollection = () => {
		if (!loginStatus) {
			alert("User must log in to access this feature!")
		}
		else {
			if (currentUser) {
				fetchAddToCollection(currentUser)
				setShowPopup(true)
				setTimeout(() => {
					setShowPopup(false)
				}, 5000)
				console.log(Users.users)
			}
			else {
				console.error("User or user.collection is undefined:", user);
			}
		}
	}

	const showModal = (isOpen) => {
		setToggleModal(isOpen)
	}

	const editComic = () => {
		setToggleModal(true)
	}

	return (
		<Card key={props.comicKey} className={"comicCard overflow-visible box_shadow text-white bg-gradient"}>
			{showPopup && (
				<div className={'popup box_shadow inner-shadow Poppins-Bold'}>
					Comic added to collection!
				</div>
			)}
			<CardHeader className={'overflow-auto Poppins-Bold'}>
				{props.comicTitle}
			</CardHeader>
			<CardBody className={"flex-column justify-content-between h-75"}>
					<p className={"Poppins-Bold m-0"}>Publisher: {publisher}</p>
					<p className={"Poppins-Bold m-0"}> Series: {series}</p>
					<p className={"Poppins-Bold m-0"}> Volume #: {volume}</p>
					<p className={"Poppins-Bold m-0"}> Issue #: {issue}</p>
			</CardBody>

			<CardFooter>
				<button className={"operation_button box_shadow bg-gradient"} onClick={() => addToCollection()}>Add to Collection </button>
			</CardFooter>
		</Card>
	)
}

export default ComicCard