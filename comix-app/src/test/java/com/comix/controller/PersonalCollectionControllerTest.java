package com.comix.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import com.comix.controller.api_controllers.PersonalCollectionController;
import com.comix.model.collection.Collection;
import com.comix.model.comic.Comic;
import com.comix.model.comic.ConcreteComic;
import com.comix.persistence.PersonalCollectionDao;
import com.comix.persistence.PersonalCollectionFileDao;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PersonalCollectionControllerTest {
    private PersonalCollectionDao PersonalCollection;
    private PersonalCollectionController controller;
    @BeforeEach
    public void setup() throws IOException{
        PersonalCollection=new PersonalCollectionFileDao(new ObjectMapper());
        controller=new PersonalCollectionController(PersonalCollection);


        // blank personal collection we will use to add comics to
        controller.createCollection("Randy");
    }
    @Test
    public void testGetCollection()throws IOException{
        // //Setup
        // String username="ryan";
        // //Invoke
        // ResponseEntity<Collection> collection=controller.getCollection(username);
        // //Analyze
        // assertEquals(HttpStatus.OK, collection.getStatusCode());
    }
    @Test
    public void testCreateCollection()throws IOException{
        // //Setup
        // String username="Bob";
        // //Invoke
        // ResponseEntity<Collection> collection=controller.createCollection(username);
        // //Analyze
        // assertEquals(HttpStatus.OK, collection.getStatusCode());
        // assertEquals(controller.getCollection("Bob").getBody().getName(), "Bob");
    }
    @Test
    public void testAddComic()throws IOException{
        //Setup
  
        //Invoke
        ResponseEntity<Comic> collection=controller.addComic("Randy", new ConcreteComic("Biki Singh", 
        "Adventures of my Wombat named Larry", 
        "Rump of Death", 
        "The Reckoning", 
        "June 2024", 
        null, 
        null, 
        null, 
        null, 
        null));
        //Analyze
        assertEquals(HttpStatus.OK, collection.getStatusCode());
        assertEquals(collection.getBody().getIssue(), "The Reckoning");
    }
    @Test
    public void testRemoveComic() throws IOException{
        //Setup
        ConcreteComic comic=new ConcreteComic ("Biki Singh",
        "Adventures of my Wombat named Larry", 
        "Rump of Death", 
        "The Reckoning", 
        "June 2024", 
        null, 
        null, 
        null, 
        null, 
        null);
        controller.addComic("Randy", comic);
        //Invoke
        ResponseEntity<Boolean> removeStatus=controller.removeComic("Randy", comic);

        //Analyze
        assertEquals(HttpStatus.OK, removeStatus.getStatusCode());
        assertEquals(removeStatus.getBody(), true);
    }
//    @Test
//    public void testUpdateComic() throws IOException{
//        //Setup
//        Comic oldComic=new ConcreteComic ("Biki Singh",
//        "Adventures of my Wombat named Larry",
//        "Rump of Death",
//        "The Reckoning",
//        "June 2024",
//        null,
//        null,
//        null,
//        null,
//        null);
//        controller.addComic("Randy", oldComic);
//
//        Comic newComic=new ConcreteComic ("Biki Singh",
//        "Adventures of a Caterpillar Mark",
//        "Rump of Death",
//        "The Reckoning",
//        "June 2024",
//        null,
//        null,
//        null,
//        null,
//        null);
//
//        //Invoke
//        ResponseEntity<Comic> updatedComic=controller.updateComic("Randy",oldComic,newComic);
//
//        //Analyze
//        assertEquals(HttpStatus.OK, updatedComic.getStatusCode());
//        assertEquals(updatedComic.getBody().getSeries(), "Adventures of a Caterpillar Mark");
//    }

        
}

