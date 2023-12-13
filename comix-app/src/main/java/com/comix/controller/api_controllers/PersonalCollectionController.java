package com.comix.controller.api_controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import com.comix.controller.action.ActionResult;
import com.comix.controller.action.AddComicAction;
import com.comix.controller.action.AuthComicAction;
import com.comix.controller.action.EditComicAction;
import com.comix.controller.action.GradeComicAction;
import com.comix.controller.action.RemoveComicAction;
import com.comix.controller.action.SignComicAction;
import com.comix.controller.action.SlabComicAction;
import com.comix.controller.action.actionHistory.History;
import com.comix.model.collection.Collection;
import com.comix.model.collection.PersonalCollection;
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
import com.comix.model.comic.ConcreteComic;
import com.comix.persistence.PersonalCollectionDao;

/**
 * Controller Class that handles the api requests to the react front end for the
 * Personal Collection tier
 * {@literal @}RestController Spring annotation which identifies this class as a
 * rest controller
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("collection")
public class PersonalCollectionController {
    @Autowired
    private PersonalCollectionDao PersonalCollectionDAO;

    private static final Logger Log = Logger.getLogger(PersonalCollectionController.class.getName());

    /**
     * Creates a Personal Collection API Controller
     * 
     * @param PersonalCollectionDao
     */
    public PersonalCollectionController(PersonalCollectionDao PersonalCollectionDao) {
        this.PersonalCollectionDAO = PersonalCollectionDao;
        Log.info("Injected properly-1-1-1-1--1--1-");
    }

    /**
     * API request method to get a specific collection by name
     * 
     * @param name Name of the personal collection that we use to search the DAO
     * @return return an object of type collection wrapped in a ResponseEntity
     * @throws IOException
     */

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/")
    @ResponseBody
    public ResponseEntity<Collection> getCollection(@RequestParam String name) throws IOException {
        Log.info("GET /collection/?name=" + name);

        Collection collection = PersonalCollectionDAO.getCollection(name);
        if (collection != null) {
            return new ResponseEntity<Collection>(collection, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * API request to create a new personal collection given a username
     * 
     * @param username A String that is passed into the dao to create a new
     * @return returns an object of type collection that represents the recently
     *         created profile
     */
    @PostMapping("/")
    public ResponseEntity<Collection> createCollection(@RequestBody String username) throws IOException {
        Log.info("Post /collection " + username);
        Collection collection = PersonalCollectionDAO.createCollection(username);
        if (collection != null) {
            return new ResponseEntity<Collection>(collection, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
        }
    }

    /**
     * API Method to add comic to the personal collectionx
     * 
     * @param name  name of the personal collection to add comic
     * @param comic comic to be added
     * @return Returns the recently added com
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/add")
    public ResponseEntity<Comic> addComic(@RequestParam String name, @RequestBody Comic comic) throws IOException {
        System.out.println();
        AddComicAction addComicAction = new AddComicAction(name, comic, PersonalCollectionDAO);
        addComicAction.createMemento();
        ActionResult<Comic> result = addComicAction.execute();
        Comic newComic = result.getResult();

        if (comic != null) {
            return new ResponseEntity<Comic>(newComic, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * API Method to remove comic from the collection
     * 
     * @param name  name of the collection
     * @param comic Comic to be removed
     * @return Boolean status on whether it was succesfully removed
     */
    @DeleteMapping("/remove")
    public ResponseEntity<Boolean> removeComic(@RequestParam String name, @RequestBody Comic comic) throws IOException {
        // boolean removeStatus=PersonalCollectionDAO.removeComic(name, comic);
        RemoveComicAction removeComicAction = new RemoveComicAction(name, comic, PersonalCollectionDAO);
        removeComicAction.createMemento();  
        Log.info((comic.toString()));
        ActionResult<Comic> result = removeComicAction.execute();
        
        Boolean removeStatus = result.getSuccess();

        if (removeStatus == true) {
            return new ResponseEntity<Boolean>(removeStatus, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false,HttpStatus.NOT_FOUND);
        }
    }

    /**
     * API request to update a comic in a specific users personal collection
     * 
     * @param name     name of the user
     * @param oldComic old version of the comic
     * @param newComic new version of the comic that will replace the old version
     * @return Comic type wrapped in response entity
     * @throws IOException 
     */
    @PutMapping("/update")
    public ResponseEntity<Comic> updateComic(@RequestParam String name, @RequestBody Map<String, Comic> comics) throws IOException {
        // Comic updatedComic = PersonalCollectionDAO.updateComic(name, oldComic,
        // newComic);
        Comic oldComic = comics.get("oldComic");
        Comic newComic = comics.get("newComic");
        EditComicAction editComicAction = new EditComicAction(name, oldComic, newComic, PersonalCollectionDAO);
        editComicAction.createMemento();
        ActionResult<Comic> result = editComicAction.execute();
        Comic updatedComic = result.getResult();

        if (updatedComic != null) {
            return new ResponseEntity<Comic>(updatedComic, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    /**
     * API request to grade a comic in a specific users personal collection
     * 
     * @param name     name of the user
     * @param oldComic old version of the comic
     * @param grade    grade for the new comic
     * @return Comic type wrapped in response entity
     * @throws IOException
     */
    @PutMapping("/grade")
    public ResponseEntity<Comic> gradeComic(@RequestParam String name, @RequestBody Comic oldComic,
            @RequestBody int grade) throws IOException {
        GradeComicAction gradeComicAction = new GradeComicAction(name, oldComic, grade, PersonalCollectionDAO);
        gradeComicAction.createMemento();
        ActionResult<Comic> result = gradeComicAction.execute();
        Comic gradedComic = result.getResult();

        if (gradedComic != null) {
            return new ResponseEntity<Comic>(gradedComic, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    /**
     * API request to slab a comic in a specific users personal collection
     * 
     * @param name      name of the user
     * @param oldComic  old version of the comic
     * @param isSlabbed slab status for the new comic
     * @return Comic type wrapped in response entity
     * @throws IOException
     */
    @PutMapping("/slab")
    public ResponseEntity<Comic> slabComic(@RequestParam String name, @RequestBody Comic oldComic,
            @RequestBody boolean isSlabbed) throws IOException {
        SlabComicAction slabComicAction = new SlabComicAction(name, oldComic, isSlabbed, PersonalCollectionDAO);
        slabComicAction.createMemento();
        ActionResult<Comic> result = slabComicAction.execute();
        Comic slabbedComic = result.getResult();

        if (slabbedComic != null) {
            return new ResponseEntity<Comic>(slabbedComic, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    /**
     * API request to sign a comic in a specific users personal collection
     * 
     * @param name      name of the user
     * @param oldComic  old version of the comic
     * @param signature signature for the new comic
     * @return Comic type wrapped in response entity
     * @throws IOException
     */
    @PutMapping("/sign")
    public ResponseEntity<Comic> signComic(@RequestParam String name, @RequestBody Comic oldComic,
            @RequestBody String signature) throws IOException {
        SignComicAction signComicAction = new SignComicAction(name, oldComic, signature, PersonalCollectionDAO);
        signComicAction.createMemento();
        ActionResult<Comic> result = signComicAction.execute();
        Comic signedComic = result.getResult();

        if (signedComic != null) {
            return new ResponseEntity<Comic>(signedComic, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    /**
     * API request to auth a comic in a specific users personal collection
     * 
     * @param name      name of the user
     * @param oldComic  old version of the comic
     * @param signature signature to be authenticated for the new comic
     * @return Comic type wrapped in response entity
     * @throws IOException
     */
    @PutMapping("/auth")
    public ResponseEntity<Comic> authComic(@RequestParam String name, @RequestBody Comic oldComic,
            @RequestBody String signature) throws IOException {
        AuthComicAction authComicAction = new AuthComicAction(name, oldComic, signature, PersonalCollectionDAO);
        authComicAction.createMemento();
        ActionResult<Comic> result = authComicAction.execute();
        Comic authComic = result.getResult();

        if (authComic != null) {
            return new ResponseEntity<Comic>(authComic, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/search")
    @ResponseBody
    public ResponseEntity<ArrayList<Comic>> searchCollection(@RequestParam String name, @RequestParam String query,
            @RequestParam String type, @RequestParam String exactMatch) throws IOException {
        PersonalCollectionVisitor visitor = new PersonalCollectionVisitor();
        Log.info("GET /search/Comics?name=" + name + "&query=" + query + "&type=" + type + "&exactMatch=" + exactMatch);
        boolean match = false;

        if (exactMatch.toLowerCase().equals("true")) {
            match = true;
        }
        ArrayList<Comic> searchResults = new ArrayList<>();
        // search by specified search type
        switch (type.toLowerCase()) {
            case "character":
                visitor.setStrategy(new CharacterSearch(match, query));
                searchResults = PersonalCollectionDAO.searchCollection(name, visitor);
                break;
            case "comictitle":
                visitor.setStrategy(new ComicTitleSearch(match, query));
                searchResults = PersonalCollectionDAO.searchCollection(name, visitor);
                break;
            case "creatorname":
                visitor.setStrategy(new CreatorNameSearch(match, query));
                searchResults = PersonalCollectionDAO.searchCollection(name, visitor);
                break;
            case "description":
                visitor.setStrategy(new DescriptionSearch(match, query));
                searchResults = PersonalCollectionDAO.searchCollection(name, visitor);
                break;
            case "issue":
                visitor.setStrategy(new IssueSearch(match, query));
                searchResults = PersonalCollectionDAO.searchCollection(name, visitor);
                break;
            case "publicationdate":
                visitor.setStrategy(new PublicationDateSearch(match, query));
                searchResults = PersonalCollectionDAO.searchCollection(name, visitor);
                break;
            case "publisher":
                visitor.setStrategy(new PublisherSearch(match, query));
                searchResults = PersonalCollectionDAO.searchCollection(name, visitor);
                break;
            case "runs":
                visitor.setStrategy(new RunsSearch());
                searchResults = PersonalCollectionDAO.searchCollection(name, visitor);
                break;
            case "gaps":
                visitor.setStrategy(new GapsSearch());
                searchResults = PersonalCollectionDAO.searchCollection(name, visitor);
                break;
            case "grade":
                visitor.setStrategy(new GradeComicSearch());
                searchResults = PersonalCollectionDAO.searchCollection(name, visitor);
                break;
            case "slab":
                visitor.setStrategy(new SlabComicSearch());
                searchResults = PersonalCollectionDAO.searchCollection(name, visitor);
                break;
            case "sign":
                visitor.setStrategy(new SignComicSearch());
                searchResults = PersonalCollectionDAO.searchCollection(name, visitor);
                break;
            default:
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<ArrayList<Comic>>(searchResults, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/undo")
    public ResponseEntity<Comic> undoCollection(@RequestParam String name)
    {
        History history = History.getHistory();
        ActionResult<Comic> result = history.undo();
        Comic comic = result.getResult();
        if (comic != null){
            return new ResponseEntity<Comic>(comic, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/redo")
    public ResponseEntity<Comic> redoCollection(@RequestParam String name)
    {
        History history = History.getHistory();
        ActionResult<Comic> result = history.redo();
        Comic comic = result.getResult();
        if (comic != null){
            return new ResponseEntity<Comic>(comic, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
