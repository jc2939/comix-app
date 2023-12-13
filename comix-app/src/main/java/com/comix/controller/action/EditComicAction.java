package com.comix.controller.action;

import com.comix.controller.action.actionHistory.ActionState;
import com.comix.controller.action.actionHistory.History;
import com.comix.controller.action.actionHistory.UndoableAction;
import com.comix.model.comic.Comic;
import com.comix.persistence.PersonalCollectionDao;

public class EditComicAction implements UndoableAction {
    private static final String COMIC_EDITED_SUCCESS_MESSAGE = "Comic edited successfully!";
    private static final String COMIC_EDITED_FAILURE_MESSAGE = "Error editing comic... Please try again.";

    private PersonalCollectionDao collectionDao;
    private String username;
    private Comic oldComic;
    private Comic newComic;

    /**
     * Constructor for an EditComicAction.
     * 
     * @param usename       The String username of the user editing the comic.
     * @param newComic      The comic to be edited.
     * @param collectionDao The DAO for the user's personal collection.
     */
    public EditComicAction(String usename, Comic oldComic, Comic newComic, PersonalCollectionDao collectionDao) {
        this.username = usename;
        this.oldComic = oldComic;
        this.newComic = newComic;
        this.collectionDao = collectionDao;
    }

    /**
     * Executes a user's action to edit a comic in their personal collection.
     * 
     * @return The result of the action, containing the comic edited.
     */
    public ActionResult<Comic> execute() {
        try {
            Comic editedComic = collectionDao.updateComic(username, oldComic, newComic);
            if (editedComic != null) {
                return new ActionResult<Comic>(true, editedComic, COMIC_EDITED_SUCCESS_MESSAGE);
            } else {
                return new ActionResult<Comic>(false, null, COMIC_EDITED_FAILURE_MESSAGE);
            }
        } catch (Exception e) {
            return new ActionResult<Comic>(false, null, COMIC_EDITED_FAILURE_MESSAGE);
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
            Comic editedComic = collectionDao.updateComic(username, newComic, oldComic);
            if (editedComic != null) {
                return new ActionResult<Comic>(true, editedComic, COMIC_EDITED_SUCCESS_MESSAGE);
            } else {
                return new ActionResult<Comic>(false, null, COMIC_EDITED_FAILURE_MESSAGE);
            }
        } catch (Exception e) {
            return new ActionResult<Comic>(false, null, COMIC_EDITED_FAILURE_MESSAGE);
        }
    }
}
