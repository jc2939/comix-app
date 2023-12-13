package com.comix.controller.api_controllers;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.xml.crypto.Data;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.comix.model.collection.visitor.PersonalCollectionVisitor;
import com.comix.model.collection.visitor.strategy.CharacterSearch;
import com.comix.model.collection.visitor.strategy.ComicTitleSearch;
import com.comix.model.collection.visitor.strategy.CreatorNameSearch;
import com.comix.model.collection.visitor.strategy.DescriptionSearch;
import com.comix.model.collection.visitor.strategy.GapsSearch;
import com.comix.model.collection.visitor.strategy.GradeComicSearch;
import com.comix.model.collection.visitor.strategy.IssueSearch;
import com.comix.model.collection.visitor.strategy.PublicationDateSearch;
import com.comix.model.collection.visitor.strategy.PublisherSearch;
import com.comix.model.collection.visitor.strategy.RunsSearch;
import com.comix.model.collection.visitor.strategy.SignComicSearch;
import com.comix.model.collection.visitor.strategy.SlabComicSearch;
import com.comix.model.comic.Comic;
import com.comix.persistence.DatabaseFileDao;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("database")
public class DatabaseController {
    private final DatabaseFileDao DatabaseDAO;
    private final PersonalCollectionVisitor visitor=new PersonalCollectionVisitor();
    private static final Logger Log=Logger.getLogger(DatabaseController.class.getName());
    public DatabaseController(DatabaseFileDao DatabaseDao){
        this.DatabaseDAO=DatabaseDao;
    }
    /**
     * API function to integrate the search functionality for a database. 
     * Works by using our previous visitor classes and setting their strategy before searching the database;
     * 
     * @param comicName The ComicName String to search the database
     * @param type type of search
     * @param exactMatch String for determining exact match
     * @return ResponseEntity<ArrayList<Comic>>, an arraylist wrapped in a response entity send back
     */
    @GetMapping("/search")
    public ResponseEntity<ArrayList<Comic>> searchDatabase(@RequestParam String query, @RequestParam String type, @RequestParam String exactMatch){
        boolean match=false;
        if(exactMatch.toLowerCase().equals("true")){
            match=true;
        }
        ArrayList<Comic> searchResults = new ArrayList<>();
        //search by specified search type
        switch (type.toLowerCase()) {
            case "character":
                visitor.setStrategy(new CharacterSearch(match, query));
                searchResults = DatabaseDAO.searchDatabase(visitor);
                break;
            case "comictitle":
                visitor.setStrategy(new ComicTitleSearch(match, query));
                searchResults = DatabaseDAO.searchDatabase(visitor);
                break;
            case "creatorname":
                visitor.setStrategy(new CreatorNameSearch(match, query));
                searchResults = DatabaseDAO.searchDatabase(visitor);
                break;
            case "description":
                visitor.setStrategy(new DescriptionSearch(match, query));
                searchResults = DatabaseDAO.searchDatabase(visitor);
                break;
            case "issue":
                visitor.setStrategy(new IssueSearch(match, query));
                searchResults = DatabaseDAO.searchDatabase(visitor);
                break;
            case "publicationdate":
                visitor.setStrategy(new PublicationDateSearch(match, query));
                searchResults = DatabaseDAO.searchDatabase(visitor);
                break;
            case "publisher":
                visitor.setStrategy(new PublisherSearch(match, query));
                searchResults = DatabaseDAO.searchDatabase(visitor);
                break;
            case "runs":
                visitor.setStrategy(new RunsSearch());
                searchResults = DatabaseDAO.searchDatabase(visitor);
                break;
            case "gaps":
                visitor.setStrategy(new GapsSearch());
                searchResults = DatabaseDAO.searchDatabase(visitor);
                break;
            default:
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<ArrayList<Comic>>(searchResults, HttpStatus.OK);
    }
}
