package com.comix.controller.api_controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.security.auth.x500.X500Principal;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.comix.model.comic.Comic;
import com.comix.model.user.User;
import com.comix.persistence.UserDao;
import com.comix.persistence.UserFileDao;

import jakarta.websocket.server.PathParam;

/**
 * Controller Class that handles the api requests to the react front end for the User tier
 * {@literal @}RestController Spring annotation which identifies this class as a rest controller
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("user")
public class UserController {
    private final UserFileDao user;
    private static final Logger Log = Logger.getLogger(UserController.class.getName());
    /**
     * Constructor for our User API Controller for us to send our requests
     * @param user The UserFileDao we want to pull our info
     */
    public UserController(UserFileDao user){
        this.user=user;
    }
    /**
     * API request to get a specific user by name only from our system
     * @param name the username of the profile we want to grab
     * @return the user profile we want to grab wrapped in an responseentity
     */
    @GetMapping("/")
    public ResponseEntity<User> getUser(@RequestParam String name){
        Log.info("GET /user/?name ="+ name);

        User getUser=user.getUser(name);

        if(user!=null){
            return new ResponseEntity<User>(getUser,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    /**
     *  API request to login in to an existing account using a username and a password. Will return the appropriate User
     *  to the front end with the information
     * @param username username of the account we want to login to
     * @param password password of the account we want to login to
     * @return returns a type of user as a response enetity to the front end displaying HttpStatus
     */

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/login")
    public ResponseEntity<User> login(@RequestParam String username, @RequestParam String password){
        User account = user.login(username, password);
        if(account!=null){
            return new ResponseEntity<User>(account,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/accounts")
    public ResponseEntity<ArrayList<User>> getUsersArray(){
        ArrayList<User> accounts = user.getUsersArray();
        return (accounts!=null) ? new ResponseEntity<ArrayList<User>>(accounts,HttpStatus.OK) : new ResponseEntity<>(HttpStatus.CONFLICT);
    }
    /**
     * API request to register an account in our system. When an account is registered a subsequence personall collection is created
     * After we register we immediately allow the user to access the program which requires the newly created user is returned
     * @param username username of the account we want to register
     * @param password password of the account we want to register
     * @return returns the newly registered user in a response entity with the HTTP status
     * @throws IOException throw possible IOExceptions that can occur with parsing the user json
     */

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/account")
    public ResponseEntity<User> createAccount(@RequestBody User newUser) throws IOException{
        User account = user.createAccount(newUser.getUsername(), newUser.getPassword());
        if (account!=null) {
            return new ResponseEntity<>(account,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
        }
    }
}
