import React, {useState} from 'react'
import {useNavigate} from "react-router-dom";
import {Users} from "../User";
import {useLogin, useUser} from "../UserContext";

const url = "http://localhost:8080"
const Login = () => {
	const [errorMessage, setErrorMessage] = useState("");
	const [error, setError] = useState(false)
	const [formData, setFormData] = useState({
		username: '',
		password: ''
	});

	// eslint-disable-next-line no-unused-vars
	const [currentUser, setCurrentUser] = useUser()
	// eslint-disable-next-line no-unused-vars
	const [isLoggedIn, setIsLoggedIn] = useLogin()

	const handleChange = (e) => {
		setErrorMessage("")
		setFormData({
			...formData,
			[e.target.name]: e.target.value,
		})
	}

	const navigate = useNavigate()

	const handleNav = (destination) => {
		navigate(destination)
	}

	const fetchLogin = async (username, password) => {

		return fetch(`${url}/user/login?username=${username}&password=${password}`)
			.then((response) => {
				if (!response.ok) {
					throw new Error(`HTTP error! Status: ${response.status}`);
				}
				return response.json();
			}).then((data) => {
				console.log(data)
				return data
			}).catch((error) => {
				console.log("Login Error: ", error)
				throw error;
		})
	}
	const handleLogin = async (e) => {
		e.preventDefault();
		try {
			const foundUser = await fetchLogin(formData.username, formData.password);
			if (foundUser && foundUser.username) {
				setCurrentUser(foundUser.username);
				setIsLoggedIn(true);
				handleNav('/home');
			} else {
				setErrorMessage('Username not found. Please try again.');
				console.log(errorMessage);
				console.log('User not found');
			}
		} catch (error) {
			setErrorMessage('Error during login. Please try again.');
			console.log(errorMessage);
			console.error(error);
		}
	};

	return (
		<div className="welcome-login box_shadow pt-5 pb-5">
			<form onSubmit={handleLogin} className="form-container">
				<div className="login-box">
					<h1 className="Poppins-Bold">Login</h1>
					<span>
            			<label className="labelFont" htmlFor="username">
              				Username:
            			</label>
            			<input
							className="input box_shadow"
							type="text"
							name="username"
							id="username"
							placeholder="Enter Username"
							value={formData.username}
							onChange={handleChange}
						/>
          			</span>
					<span>
						<label className="labelFont" htmlFor="password">
						  Password:
						</label>
						<input
							className="input box_shadow"
							type="password"
							name="password"
							id="password"
							placeholder="Enter Password"
							value={formData.password}
							onChange={handleChange}
						/>
          			</span>
					{error && (
						<div className={'Poppins-Bold error'}>
							{errorMessage}
						</div>
					)}
					<div className="button-container">
						<button className={"login-button box_shadow"} type={'submit'}>
							Login
						</button>
						<button className={"login-button box_shadow"} onClick={() => handleNav("/signup")}>
							Sign Up
						</button>
					</div>
				</div>
			</form>
		</div>
	);
};

export default Login