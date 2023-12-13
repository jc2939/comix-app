package com.comix.controller.action;

import com.comix.controller.action.actionHistory.ActionState;
import com.comix.controller.action.actionHistory.History;
import com.comix.controller.action.actionHistory.UndoableAction;
import com.comix.model.comic.Comic;
import com.comix.model.comic.SlabDecorator;
import com.comix.persistence.PersonalCollectionDao;

public class SlabComicAction implements UndoableAction {
    private static final String COMIC_SLABBED_SUCCESS_MESSAGE = "Comic slabbed successfully!";

    private PersonalCollectionDao collectionDao;
    private String username;
    private Comic comic;
    private boolean isSlabbed;

    /**
     * Constructor for a SlabComicAction.
     * 
     * @param username      The string username of the user slabbing the comic.
     * @param comic         The comic to be slabbed.
     * @param isSlabbed     The status of whether the comic is slabbed or not.
     * @param collectionDao The DAO for the user's personal collection.
     */
    public SlabComicAction(String username, Comic comic, boolean isSlabbed, PersonalCollectionDao collectionDao) {
        this.username = username;
        this.comic = comic;
        this.isSlabbed = isSlabbed;
        this.collectionDao = collectionDao;
    }

    /**
     * Executes a user's action to slab a comic in their personal collection.
     * 
     * @return The result of the action, containing the slabbed Comic.
     */
    public ActionResult<Comic> execute() {
        try {
            SlabDecorator<Comic> slabbedComic = new SlabDecorator<>(comic, isSlabbed);
            // collectionDao.removeComic(username, comic);
            Comic newComic = collectionDao.addComic(username, slabbedComic);
            return new ActionResult<Comic>(true, newComic, COMIC_SLABBED_SUCCESS_MESSAGE);
        } catch (Exception e) {
            return new ActionResult<Comic>(false, null, e.getMessage());

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
            Comic undoneComic = collectionDao.addComic(username, comic);
            if (undoneComic != null) {
                return new ActionResult<Comic>(true, undoneComic, "Comic unslabbed successfully.");
            } else {
                return new ActionResult<Comic>(false, null, "Unslabbing unsuccessful, please try again.");
            }
        }
        catch (Exception e) {
            return new ActionResult<Comic>(false, null, "Unslabbing unsuccessful, please try again.");
        }
    }
}
