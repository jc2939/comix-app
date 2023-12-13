import React, {useState} from 'react'
import './Styles/App.css';
import NavBar from "./Components/NavBar";
import ComicBoard from "./Components/ComicBoard";
import {Routes, Route, BrowserRouter} from "react-router-dom"
import Login from "./Components/Login";
import SignUp from "./Components/SignUp";
import MyCollection from "./Components/MyCollection";
import {UserProvider} from "./UserContext";
function App() {
    const [comics, setComics] = useState([])
    const [users, setUsers] = useState([])
    return (
        <UserProvider>
            <BrowserRouter>
                <div className="App h-100">
                    <NavBar
                        comics={comics}
                        setComics={setComics}
                    />
                    <div className={"App vh-100 p-5"}>
                        <Routes>
                            <Route path={"/home"} element={<ComicBoard
                                                                comics={comics}/>}/>
                            <Route path={"/login"} element={<Login
                                                users={users}/>}/>
                            <Route path={"/signup"} element={<SignUp/>}/>
                            <Route path={"/mycollection"} element={<MyCollection/>}/>
                        </Routes>
                    </div>
                </div>
            </BrowserRouter>
        </UserProvider>
  );
}

export default App;
