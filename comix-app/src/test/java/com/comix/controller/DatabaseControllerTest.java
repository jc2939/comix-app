package com.comix.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.comix.controller.api_controllers.DatabaseController;
import com.comix.model.comic.Comic;
import com.comix.persistence.DatabaseDao;
import com.comix.persistence.DatabaseFileDao;
import com.fasterxml.jackson.databind.ObjectMapper;

@Testable
public class DatabaseControllerTest {
    private DatabaseFileDao database;
    private DatabaseController databaseController;
    @BeforeEach
    public void setup() throws IOException{
        //Initialize our database dao with the object mapper
        database=new DatabaseFileDao(new ObjectMapper());
        //pass in our database to our database REST controller
        databaseController=new DatabaseController(database);
    }

    @Test
    public void testBaseSearch() throws IOException{
        //Setup
        String query="spider";
        String type="comictitle";
        String exactMatch="false";
        //Invoke
        ResponseEntity<ArrayList<Comic>> searchResults=databaseController.searchDatabase(query, type, exactMatch);
        //Analyze
        assertEquals(HttpStatus.OK, searchResults.getStatusCode());
        assertNotEquals(0, searchResults.getBody().size());
        assertEquals("A Candy Full Of Spiders", searchResults.getBody().get(0).getComicTitle());
    }
}
