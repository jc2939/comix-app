package com.comix.controller.action;

import com.comix.controller.action.actionHistory.ActionState;
import com.comix.controller.action.actionHistory.History;
import com.comix.controller.action.actionHistory.UndoableAction;
import com.comix.model.comic.Comic;
import com.comix.model.comic.GradeDecorator;
import com.comix.persistence.PersonalCollectionDao;

public class GradeComicAction implements UndoableAction {
    private static final String COMIC_GRADED_SUCCESS_MESSAGE = "Comic graded successfully!";
    private static final String COMIC_GRADED_FAILURE_MESSAGE = "Error grading comic... Please try again.";

    private PersonalCollectionDao collectionDao;
    private String username;
    private Comic comic;
    private int grade;

    /**
     * Constructor for a GradeComicAction.
     * 
     * @param username      The String username of the user grading the comic.
     * @param comic         The comic to be graded.
     * @param grade         The grade to be assigned to the comic.
     * @param collectionDao The DAO for the user's personal collection.
     */
    public GradeComicAction(String username, Comic comic, int grade, PersonalCollectionDao collectionDao) {
        this.username = username;
        this.comic = comic;
        this.grade = grade;
        this.collectionDao = collectionDao;
    }

    /**
     * Executes a user's action to grade a comic in their personal collection.
     * 
     * @return The result of the action, containing the ID of the graded comic.
     */
    public ActionResult<Comic> execute() {
        // construct the decorator, and pass in the comic and grade
        try {
            GradeDecorator<Comic> gradedComic = new GradeDecorator<>(comic, grade);
            // collectionDao.removeComic(username, comic);
            collectionDao.addComic(username, gradedComic);
            return new ActionResult<Comic>(true, gradedComic, COMIC_GRADED_SUCCESS_MESSAGE);
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
                return new ActionResult<Comic>(true, undoneComic, "Comic ungraded successfully.");
            } else {
                return new ActionResult<Comic>(false, null, "Ungrade unsuccessful, please try again.");
            }
        }
        catch (Exception e) {
            return new ActionResult<Comic>(false, null, "Ungrade unsuccessful, please try again.");
        }
    }
}
