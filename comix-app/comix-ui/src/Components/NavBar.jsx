import {useNavigate} from 'react-router-dom'
import {useEffect} from "react";
import {useLogin, useUser} from "../UserContext";
import Searchbar from './Searchbar'
const NavBar = (props) => {
	const {comics, setComics} = props;
	const [currentUser, setCurrentUser] = useUser()
	const [loginStatus, setLoginStatus] = useLogin()

	useEffect(() => {
		console.log("Current User:", currentUser);
		console.log("Current Login Status: ", loginStatus)
	}, [currentUser, loginStatus]);

	const navigate = useNavigate();
	const handleNav = (destination) => {
		navigate(destination)
	}

	const handleLogout = () => {
		console.log("User has logged out!")
		setLoginStatus(false)
		setCurrentUser('Guest')
	}

	return (
		<div className="navBar w-100 h-100 justify-content-between p-3">
				<h1 className="m-3 Poppins-Bold logo font-bolder">
					Comix
				</h1>
				<Searchbar
					comics={comics}
					setComics={setComics}
				/>

			<div className="button-container Poppins-Bold">
				<button className="navButton box_shadow bg-gradient" onClick={() => handleNav("/home")}> Home </button>
				<button className="navButton box_shadow bg-gradient"
						onClick={() => {loginStatus ? handleLogout() : handleNav("/login")}}> {loginStatus ? 'Log Out' : 'Login' }</button>

				{loginStatus && (
					<button className="navButton box_shadow bg-gradient" onClick={() => handleNav("/mycollection")}> My Collection </button>
					)}
			</div>
			<div>
				<img className={"user_icon"} src={"https://cdn-icons-png.flaticon.com/256/5989/5989400.png"} alt={"User Icon"}/>
				<p>Hello, {currentUser}</p>
			</div>
		</div>
	);
}

export default NavBar;