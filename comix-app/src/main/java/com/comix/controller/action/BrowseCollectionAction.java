package com.comix.controller.action;

import com.comix.model.collection.Collection;
import com.comix.persistence.PersonalCollectionDao;

public class BrowseCollectionAction implements Action<ActionResult<Collection>> {
    private static final String COLLECTION_BROWSE_EMPTY_MESSAGE = "You have no comics in your collection... Add some!";
    private static final String COLLECTION_BROWSE_FAILURE_MESSAGE = "Error retrieving collection... Please try again.";
    private static final String COLLECTION_BROWSE_SUCCESS_MESSAGE = "Collection retrieved successfully!";

    private PersonalCollectionDao collectionDao;
    private String username;

    /**
     * Constructor for a BrowseCollectionAction.
     * 
     * @param username      The String username of the user browsing their personal
     *                      collection.
     * @param collectionDao The DAO for the user's personal collection.
     */
    public BrowseCollectionAction(String username, PersonalCollectionDao collectionDao) {
        this.username = username;
        this.collectionDao = collectionDao;
    }

    /**
     * Executes a user's action to browse their personal collection.
     * 
     * @return The result of the action, containing the user's personal collection.
     */
    public ActionResult<Collection> execute() {
        try {
            Collection collection = collectionDao.getCollection(username);
            if (collection == null) {
                return new ActionResult<Collection>(false, null, COLLECTION_BROWSE_FAILURE_MESSAGE);
            }
            if (collection.getComics().isEmpty()) {
                return new ActionResult<Collection>(true, collection, COLLECTION_BROWSE_EMPTY_MESSAGE);
            }
            return new ActionResult<Collection>(true, collection, COLLECTION_BROWSE_SUCCESS_MESSAGE);
        } catch (Exception e) {
            return new ActionResult<Collection>(false, null, COLLECTION_BROWSE_FAILURE_MESSAGE);
        }
    }

}
