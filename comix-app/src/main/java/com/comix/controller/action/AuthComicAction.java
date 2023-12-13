package com.comix.controller.action;

import com.comix.controller.action.actionHistory.UndoableAction;

import java.io.IOException;

import com.comix.controller.action.actionHistory.ActionState;
import com.comix.controller.action.actionHistory.History;
import com.comix.model.comic.Comic;
import com.comix.model.comic.SignatureDecorator;
import com.comix.persistence.PersonalCollectionDao;

public class AuthComicAction implements UndoableAction{
    private static final String COMIC_AUTH_SUCCESS_MESSAGE = "Comic signature authenticated successfully!";
    private static final String COMIC_AUTH_FAILURE_MESSAGE = "Error authenticating comic signature... Please try again.";

    private PersonalCollectionDao collectionDao;
    private String username;
    private Comic comic;
    private String signature;

    /**
     * Constructor for a GradeComicAction.
     * 
     * @param username      The String username of the user grading the comic.
     * @param comic         The comic to be graded.
     * @param grade         The grade to be assigned to the comic.
     * @param collectionDao The DAO for the user's personal collection.
     */
    public AuthComicAction(String username, Comic comic, String signature, PersonalCollectionDao collectionDao) {
        this.username = username;
        this.comic = comic;
        this.signature = signature;
        this.collectionDao = collectionDao;
    }

    /**
     * Executes a user's action to authenticate a comic signature in their personal collection.
     * 
     * @return The result of the action, containing the ID of the authenticated comic.
     */
    @Override
    public ActionResult<Comic> execute() {
        try {
            SignatureDecorator<Comic> authenticatedComic = new SignatureDecorator<>(comic, signature, true);
            collectionDao.addComic(username, authenticatedComic); // if the SignatureDecorator doesn't already exist
            return new ActionResult<Comic>(true, authenticatedComic, COMIC_AUTH_SUCCESS_MESSAGE);
        } catch (Exception e) {
            try {
                collectionDao.addComic(username, comic); // if the SignatureDecorator does already exist... (should always use this one).
            } catch (IOException e1) {
                return new ActionResult<Comic>(false, null, COMIC_AUTH_FAILURE_MESSAGE);
            }
            return new ActionResult<Comic>(true, comic, COMIC_AUTH_SUCCESS_MESSAGE);
        }
    }

    /**
     * Creates a memento that represents this action and stores it in History.
     */
    @Override
    public void createMemento() {
        History history = History.getHistory();
        ActionState state = new ActionState(this);
        history.addAction(state);
    }

    /**
     * Executes a user's action to unauthenticate a comic signiture in their personal collection.
     * 
     * @return The result of the action, containing the ID of the unauthenticated comic.
     */
    @Override
    public ActionResult<Comic> restoreState() {
        try {
            SignatureDecorator<Comic> authenticatedComic = new SignatureDecorator<>(comic, signature, false);
            collectionDao.addComic(username, authenticatedComic); // if the SignatureDecorator doesn't already exist
            return new ActionResult<Comic>(true, authenticatedComic, "Comic signature unauthenticated successfully!");
        } catch (Exception e) {
            try {
                collectionDao.addComic(username, comic); // if the SignatureDecorator does already exist... (should always use this one).
            } catch (IOException e1) {
                return new ActionResult<Comic>(false, null, "Error unauthenticating comic signature... Please try again.");
            }
            return new ActionResult<Comic>(true, comic, "Comic signature unauthenticated successfully!");
        }
    }
    
}
