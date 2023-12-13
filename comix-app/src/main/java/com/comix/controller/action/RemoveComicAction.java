package com.comix.controller.action;

import java.io.IOException;

import com.comix.controller.action.actionHistory.ActionState;
import com.comix.controller.action.actionHistory.History;
import com.comix.controller.action.actionHistory.UndoableAction;
import com.comix.model.comic.Comic;
import com.comix.persistence.PersonalCollectionDao;

public class RemoveComicAction implements UndoableAction {
    private static final String COMIC_REMOVED_SUCCESS_MESSAGE = "Comic removed successfully.";
    private static final String COMIC_REMOVED_FAILURE_MESSAGE = "Error removing comic... Please try again.";

    private PersonalCollectionDao collectionDao;
    private String username;
    private Comic comic;

    /**
     * Constructor for a RemoveComicAction.
     * 
     * @param username      The string ussername of the user removing the comic.
     * @param oldComic      The comic to be removed.
     * @param collectionDao The DAO for the user's personal collection.
     */
    public RemoveComicAction(String username, Comic comic, PersonalCollectionDao collectionDao) {
        this.username = username;
        this.comic = comic;
        this.collectionDao = collectionDao;
    }

    /**
     * Executes a user's action to remove a comic from their personal collection.
     * 
     * @return The result of the action, containing the Comic removed.
     */
    public ActionResult<Comic> execute() {
        try {
            boolean result = collectionDao.removeComic(username, comic);
            if (result) {
                return new ActionResult<Comic>(true, comic, COMIC_REMOVED_SUCCESS_MESSAGE);
            } else {
                return new ActionResult<Comic>(false, null, COMIC_REMOVED_FAILURE_MESSAGE);
            }
        } catch (Exception e) {
            return new ActionResult<Comic>(false, null, COMIC_REMOVED_FAILURE_MESSAGE);
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
            Comic addedComic = collectionDao.addComic(username, comic);
            if (addedComic != null) {
                return new ActionResult<Comic>(true, addedComic, "Comic added successfully");
            } else {
                return new ActionResult<Comic>(false, null, "Error adding comic... TRY AGAIN");
            }
        } catch (IOException e) {
            return new ActionResult<Comic>(false, null, "Error adding comic... TRY AGAIN");
        }
    }
}
