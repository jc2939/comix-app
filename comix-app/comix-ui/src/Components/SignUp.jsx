import React, {useState} from "react";
import { useNavigate } from "react-router-dom";
import { Users } from "../User";
import User from "../User";
import {useLogin, useUser} from "../UserContext";

const url = "http://localhost:8080"
const SignUp = () => {
	const [currentUser, setCurrentUser] = useUser();
	const [loginStatus, setLoginStatus] = useLogin();
	const [errorMessage, setErrorMessage] = useState("");

	const [formData, setFormData] = useState({
		username: "",
		password: "",
	});

	const handleChange = (e) => {
		setFormData({
			...formData,
			[e.target.name]: e.target.value,
		});
	};

	const navigate = useNavigate();
	const handleNav = (destination) => {
		navigate(destination);
	};

	const handleLogin = (username, password) => {
		const foundUser = Users.users.find((user) => user.username === formData.username);
		if (foundUser) {
			setCurrentUser(foundUser.username);
			setLoginStatus(true);
		}
	};

	const getLogin = (username, password) => {
		fetch(`${url}/user/login`).then((response) => {
			response.json()
				.then((data) => {

				})
		})
	}

	const handleSubmit = (event) => {
		console.log("Form Submitted")
		event.preventDefault()
		let formValid = true;
		if (!formData.username || !formData.password) {
			formValid = false;
		}
		if (!formValid) {
			setErrorMessage("Invalid username or password!");
		} else {
			signUp(formData.username, formData.password);

		}
	};

	const signUp = (username, password) => {
		const json = JSON.stringify({
			username: username,
			password: password
		})
		fetch(`${url}/user/account`, {
			method: 'PUT',
			body:(json),
			headers: {
				'Content-type': 'application/json; charset=UTF-8'
			}
		})
			.then((response) => {
				if (!response.ok) {
					console.log(response);
					throw new Error(`HTTP error, status: ${response.status}`);
				}
				console.log(response);
				return response.json();
			})
			.then((data) => {
				console.log(data);
			})
			.catch((error) => {
				console.error("Error with signup functionality", error);
			});
	};

	return (
		<div className="welcome-login box_shadow pt-5 pb-5">
			<form onSubmit={handleSubmit} className="form-container">
				<div className="login-box">
					<h1 className="Poppins-Bold">Sign Up</h1>
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
					<div>{errorMessage}</div>
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
					<div className="button-container">
						<button className={"login-button box_shadow"} type={"submit"}>
							Sign Up
						</button>
					</div>
				</div>
			</form>
		</div>
	);
};

export default SignUp;