import {ButtonGroup, Container, Dropdown, DropdownItem, DropdownMenu, DropdownToggle, Button} from "reactstrap";
import React, {useEffect, useState} from "react";
import '../Styles/App.css'
import { FaSearch } from 'react-icons/fa'
import {useUser} from "../UserContext";

const CollectionSearchBar = (props) => {
    const url = "http://localhost:8080"
    const [search, setSearch] = useState(
        {query: "", parameter: "", match: ""}
    )
    const [currentUser] = useUser()
    const [dropdownOpen, setDropdownOpen] = useState(false)
    const [toggleLabel, setToggleLabel] = useState("Search By...")

    useEffect(() => {
        console.log(props.comics)
    })
    const toggle = () => setDropdownOpen((prevState) => !prevState)

    const handleInputChange = (event) => {
        const {name, value} = event.target;
        setSearch((prevState) => ({
            ...prevState,
            [name]: value,
        }))
    }

    const handleSubmit = (event) => {
        event.preventDefault()
        console.log(search.query, search.parameter)
        searchComics(currentUser, search.query, search.parameter, false)
    }
    const searchComics = (name, query, type, exactMatch) => {
        fetch(`${url}/collection/search?name=${name}&query=${query}&type=${type}&exactMatch=${exactMatch}`)
            .then((response) =>
                response.json())
            .then((data) => {
                props.setComics(data)
                console.log("Comic data: ", data)
            }).catch((error) => {
            console.log("Error: ", error)
        })
    }
    const handleItemClick = (item) => {
        setToggleLabel(item);
        setSearch((prevState) => ({
            ...prevState,
            parameter: item,
        }))
    }

    return (
        <Container>
            <form onSubmit={handleSubmit}>
                <ButtonGroup className={'w-100 d-flex flex-row justify-content-center align-items-center'}>
                    <Button className={'search_button bg-gradient box_shadow'} type={'submit'}>
                        <FaSearch />
                    </Button>
                    <input
                        readOnly={false}
                        name={'query'}
                        className="search box_shadow bg-gradient"
                        placeholder="Search your personal collection"
                        value={search.query}
                        onChange={handleInputChange}/>
                    <Dropdown
                        isOpen={dropdownOpen}
                        toggle={toggle}
                        direction={'down'}>
                        <DropdownToggle className={'my_dropdown box_shadow bg-gradient'} caret>{toggleLabel}</DropdownToggle>
                        <DropdownMenu>
                            <DropdownItem onClick={() => handleItemClick("character")}>Character</DropdownItem>
                            <DropdownItem onClick={() => handleItemClick("comictitle")}>Comic Title</DropdownItem>
                            <DropdownItem onClick={() => handleItemClick("creatorname")}>Creator Name</DropdownItem>
                            <DropdownItem onClick={() => handleItemClick("description")}>Description</DropdownItem>
                            <DropdownItem onClick={() => handleItemClick("publisher")}>Publisher</DropdownItem>
                            <DropdownItem onClick={() => handleItemClick("issue")}>Issue</DropdownItem>
                            <DropdownItem onClick={() => handleItemClick("publicationdate")}>Publication Date</DropdownItem>
                        </DropdownMenu>
                    </Dropdown>
                </ButtonGroup>
            </form>
        </Container>
    )
}

export default CollectionSearchBar;