package com.comix.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;

import com.comix.persistence.PersonalCollectionDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.platform.commons.annotation.Testable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.comix.controller.api_controllers.UserController;
import com.comix.model.user.User;
import com.comix.persistence.PersonalCollectionFileDao;
import com.comix.persistence.UserFileDao;
import com.fasterxml.jackson.databind.ObjectMapper;

@Testable
public class UserControllerTest {
    private UserController userController;
    private UserFileDao users;
    private PersonalCollectionDao personalCollectionDao;
    @BeforeEach
    public void setup() throws IOException{
        
        //Initialize our file dao with the object mapper
        users=new UserFileDao(new ObjectMapper(), personalCollectionDao);
        users.setPersonalCollectionDao(new PersonalCollectionFileDao(new ObjectMapper()));
        //pass the file dao into the creation of our UserController
        userController= new UserController(users);
    }
    @Test
    public void testGetUser(){
        //Setup
        String username = "Alec";
        //Invoke
        ResponseEntity<User> user = userController.getUser(username);
        //Analyze
        assertEquals(HttpStatus.OK, user.getStatusCode());
        assertEquals("Alec", user.getBody().getUsername());
        assertEquals("pass", user.getBody().getPassword());        
    }
    @Test
    public void testLogin() {
        //Setup
        String username = "newuser";
        String password = "pass";
        //Invoke
        ResponseEntity<User> user=userController.login(username, password);
        //Analyze
        assertEquals(HttpStatus.OK, user.getStatusCode());
        assertEquals(username, user.getBody().getUsername());
        assertEquals(password, user.getBody().getPassword());
    }
//    @Test
//    public void testcreateAccount()throws IOException{
//        //Setup
//        String username = "Mercenary";
//        String password = "RiskOfRain";
//        //Invoke
//        ResponseEntity<User> user=userController.createAccount(username, password);
//        //Analyze
//        assertEquals(HttpStatus.ALREADY_REPORTED, user.getStatusCode());
//
//    }
    @Test
    public void testgetUsersArray(){
        //Setup
        ResponseEntity<ArrayList<User>> accounts;
        //Invoke
        accounts=userController.getUsersArray();
        //Analyze
        assertEquals(HttpStatus.OK, accounts.getStatusCode());
        assertEquals("Alec",accounts.getBody().get(0).getUsername());
    }
    
    
}
