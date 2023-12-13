package com.comix.controller.action;

import java.io.IOException;

import com.comix.controller.action.actionHistory.UndoableAction;
import com.comix.model.comic.Comic;
import com.comix.persistence.PersonalCollectionDao;
import com.comix.controller.action.actionHistory.ActionState;
import com.comix.controller.action.actionHistory.History;

public class AddComicAction implements UndoableAction {
    private static final String COMIC_ADDED_SUCCESS_MESSAGE = "Comic added successfully!";
    private static final String COMIC_ADDED_FAILURE_MESSAGE = "Error adding comic... Please try again.";

    private PersonalCollectionDao collectionDao;
    private String username;
    private Comic comic;

    /**
     * Constructor for an AddComicAction.
     * 
     * @param username      The String username of the user adding the comic.
     * @param comic         The comic to be added.
     * @param collectionDao The DAO for the user's personal collection.
     */
    public AddComicAction(String username, Comic comic, PersonalCollectionDao collectionDao) {
        this.username = username;
        this.comic = comic;
        this.collectionDao = collectionDao;
    }

    /**
     * Executes a user's action to add a comic to their personal collection.
     * 
     * @return The result of the action, containing the ID of the comic added.
     */
    public ActionResult<Comic> execute() {
        try {
            Comic addedComic = collectionDao.addComic(username, comic);
            if (addedComic != null) {
                return new ActionResult<Comic>(true, addedComic, COMIC_ADDED_SUCCESS_MESSAGE);
            } else {
                return new ActionResult<Comic>(false, null, COMIC_ADDED_FAILURE_MESSAGE);
            }
        } catch (IOException e) {
            return new ActionResult<Comic>(false, null, COMIC_ADDED_FAILURE_MESSAGE);
        }
    }

    @Override
    public void createMemento() {
        History history = History.getHistory();
        ActionState state = new ActionState(this);
        history.addAction(state);
    }

    @Override
    public ActionResult<Comic> restoreState() { 
        try {
            boolean result = collectionDao.removeComic(username, comic);
            if (result)
                return new ActionResult<Comic>(true, comic, "Comic removed successfully");
            else
                return new ActionResult<Comic>(false, comic, "Error removing comic... TRY AGAIN");
        }
        catch (Exception e) {
            return new ActionResult<Comic>(false, null, "Error removing comic... TRY AGAIN");
        }
    }
}
