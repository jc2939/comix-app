package com.comix.controller.action;

import java.io.IOException;

import com.comix.controller.action.actionHistory.ActionState;
import com.comix.controller.action.actionHistory.History;
import com.comix.controller.action.actionHistory.UndoableAction;
import com.comix.model.comic.Comic;
import com.comix.model.comic.SignatureDecorator;
import com.comix.persistence.PersonalCollectionDao;

public class SignComicAction implements UndoableAction {
    private static final String COMIC_SIGN_SUCCESS_MESSAGE = "Comic signed successfully!";
    private static final String COMIC_SIGN_FAILURE_MESSAGE = "Error signing comic... Please try again.";

    private PersonalCollectionDao collectionDao;
    private String username;
    private Comic comic;
    private String signature;

    /**
     * Constructor for a SignComicAction.
     * 
     * @param username      The String username of the user signing the comic.
     * @param comic         The comic to be graded.
     * @param signature     The signature to be assigned to the comic.
     * @param collectionDao The DAO for the user's personal collection.
     */
    public SignComicAction(String username, Comic comic, String signature, PersonalCollectionDao collectionDao) {
        this.username = username;
        this.comic = comic;
        this.signature = signature;
        this.collectionDao = collectionDao;
    }

    /**
     * Executes a user's action to sign a comic in their personal collection.
     * 
     * @return The result of the action, containing the ID of the signed comic.
     */
    @Override
    public ActionResult<Comic> execute() {
        try {
            SignatureDecorator<Comic> signedComic = new SignatureDecorator<>(comic, signature, false);
            collectionDao.addComic(username, signedComic); // if the SignatureDecorator doesn't already exist
            return new ActionResult<Comic>(true, signedComic, COMIC_SIGN_SUCCESS_MESSAGE);
        } catch (Exception e) {
            try {
                collectionDao.addComic(username, comic); // if the SignatureDecorator does already exist
            } catch (IOException e1) {
                return new ActionResult<Comic>(false, null, COMIC_SIGN_FAILURE_MESSAGE);
            }
            return new ActionResult<Comic>(true, comic, COMIC_SIGN_SUCCESS_MESSAGE);
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
     * Executes a user's action to sign a comic in their personal collection.
     * 
     * @return The result of the action, containing the ID of the signed comic.
     */
    @Override
    @SuppressWarnings("unchecked") // Should never be capable of calling this without it being a SignatureDecorator
    public ActionResult<Comic> restoreState() {
        try {
            Comic oldComic = comic;
            while (oldComic != null){ // Because these are pointers, it should remove the signature no matter where the decorator is in the decorator
                if (oldComic instanceof SignatureDecorator){
                    SignatureDecorator<Comic> signatureComic = ((SignatureDecorator<Comic>)oldComic); 
                    signatureComic.removeSignature(signature);
                }
                oldComic = oldComic.getComic();
            }
            collectionDao.addComic(username, comic);
            return new ActionResult<Comic>(true, comic, "Comic unsigned successfully");
        } catch (IOException e1) {
            return new ActionResult<Comic>(false, null, "Error unsigning comic... Please try again.");
        }
    }
    
}
