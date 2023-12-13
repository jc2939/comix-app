import {
    Button,
    Dropdown,
    DropdownItem,
    DropdownMenu,
    DropdownToggle, Form, Label,
    Modal,
    ModalBody,
    ModalFooter,
    ModalHeader
} from "reactstrap";
import React, {useState} from "react";
import {useLogin, useUser} from "../UserContext";

const url = "http://localhost:8080"

const CreateComicModal = (props) => {
    const [currentUser] = useUser()
    const [createComicForm, setCreateComicForm] = useState({
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

    const handleInput = (event) => {
        const {name, value} = event.target;
        setCreateComicForm((prevState) => ({
            ...prevState,
            [name]: value,
        }));
        console.log(createComicForm) 
    }

    const postNewComic = (publisher, series, volume, issue,
                          publicationDate, comicTitle, creators,
                          principleCharacters, description, value) => {
                            let creatorList = []
                            if(creators !== undefined) {
                                creatorList = creators.split(', ')
                            } 
                            // console.log(creatorList);
                            let principleCharactersList = []
                            if(principleCharacters !== undefined) {
                                principleCharactersList = creators.split(', ')
                            } 
                            // console.log(principleCharactersList);
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
                            console.log(JSON.stringify(comic))
                            fetch(`${url}/collection/add?name=${currentUser}`, {
                                method: "PUT",
                                body: JSON.stringify(comic),
                                headers: {
                                    'Content-type': 'application/json; charset=UTF-8'
                                }
                            }).then((response) => {
                                props.onSubmit(currentUser)
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
        let formValid = true;
        for (const key in createComicForm){
            if(!createComicForm[key]) {
                formValid = false;
                break;
            }
        }
        if (!formValid) {
            alert("Missing form input. Please try again.")
        }
        else {
            props.setIsOpen(false)
            postNewComic(
                createComicForm.publisher,
                createComicForm.series,
                createComicForm.volume,
                createComicForm.issue,
                createComicForm.publicationDate,
                createComicForm.comicTitle,
                createComicForm.creators,
                createComicForm.principleCharacters,
                createComicForm.description,
                createComicForm.value
            )
        }


    }

    return (
        <Modal isOpen={props.isOpen}>
            <ModalHeader className={'box_shadow bg-gradient'}> Create New Comic </ModalHeader>
            <Form onSubmit={handleSubmit}>
                <ModalBody className={'d-flex flex-column align-content-lg-start'}>
                    <Label >
                        Publisher:{" "}
                        <input
                            className={'w-75'}
                            name={"publisher"}
                            placeholder={'Enter a publisher name'}
                            value={createComicForm.publisher}
                            onChange={handleInput}
                        />
                    </Label>
                    <Label>
                        Series:{" "}
                        <input
                            name={"series"}
                            value={createComicForm.series}
                            placeholder={'Enter a series name'}
                            onChange={handleInput}/>
                    </Label>
                    <Label>
                        Volume #:{" "}
                        <input
                            name={"volume"}
                            value={createComicForm.volume}
                            placeholder={'Enter a volume'}
                            onChange={handleInput}
                        />
                    </Label>
                    <Label>
                        Issue #: {" "}
                        <input
                            name={"issue"}
                            onChange={handleInput}
                            value={createComicForm.issue}
                            placeholder={"Enter an issue number"}/>
                    </Label>
                    <Label>
                        Publication Date: {" "}
                        <input
                            name={"publicationDate"}
                            value={createComicForm.publicationDate}
                            type={"date"}
                            placeholder={"Enter a date"}
                            onChange={handleInput}/>
                    </Label>
                    <Label>
                        Comic Title: {" "}
                        <input
                            name={"comicTitle"}
                            value={createComicForm.comicTitle}
                            placeholder={"Enter a title"}
                            onChange={handleInput}/>
                    </Label>
                    <Label>
                        Creators: {" "}
                        <input
                            name={"creators"}
                            type={"text"}
                            value={createComicForm.creators}
                            placeholder={"Separate by ', '"}
                            onChange={handleInput}/>
                    </Label>
                    <Label>
                        Principle Characters: {" "}
                        <input
                            name={"principleCharacters"}
                            type={"text"}
                            value={createComicForm.principleCharacters}
                            placeholder={"Separated by ', '"}
                            onChange={handleInput}/>
                    </Label>
                    <Label>
                        Description: {" "}
                        <input
                            name={"description"}
                            value={createComicForm.description}
                            placeholder={"Enter a description"}
                            onChange={handleInput}/>
                    </Label>
                    <Label>
                        Value: {" "}
                        <input
                            name={"value"}
                            type="number" min="0.01" step="0.01" max="1000000"
                            value={createComicForm.value}
                            placeholder={"Enter the comics value"}
                            onChange={handleInput}/>
                    </Label>
                </ModalBody>
                <ModalFooter>
                    <Button type={'submit'} color={'primary bg-gradient'}>
                        Submit
                    </Button>
                    <Button onClick={() => props.setIsOpen(false)} color={'danger bg-gradient'}>
                        Cancel
                    </Button>
                </ModalFooter>
            </Form>
        </Modal>
    )
}

export default CreateComicModal;