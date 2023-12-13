package com.comix.persistence;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.comix.model.collection.Collection;
import com.comix.model.collection.visitor.CollectionVisitor;
import com.comix.model.comic.Comic;

public interface PersonalCollectionDao {
    /**
     * Creates a new collection for the user
     * 
     * @param username the username of the user
     * @return the new collection or null if the user already has a collection
     * @throws IOException if there is an error creating the collection
     */
    public Collection createCollection(String username) throws IOException;

    /**
     * Gets the collection for the user
     * 
     * @param username the username of the user
     * @return the collection for the user or null if the user doesn't have a
     *         collection
     */
    public Collection getCollection(String username);

    /**
     * Search for comics in the user's collection
     * 
     * @param username the username of the user
     * @param visitor  the visitor to use to search the collection
     * @return the list of comics that match the search criteria
     */
    public ArrayList<Comic> searchCollection(String username, CollectionVisitor visitor);

    /**
     * Adds a comic to the user's collection
     * 
     * @param username the username of the user
     * @param comic    the comic to add
     * @return the comic that was added or null if the comic was already in the
     *         collection
     * @throws IOException if there is an error adding the comic
     */
    public Comic addComic(String username, Comic comic) throws IOException;

    /**
     * Removes a comic from the user's collection
     * 
     * @param username the username of the user
     * @param comic    the comic to remove
     * @return true if the comic was removed or false if the comic was not in the
     *         collection
     * @throws IOException if there is an error removing the comic
     */
    public boolean removeComic(String username, Comic comic) throws IOException;

    /**
     * Updates a comic in the user's collection
     * 
     * @param username the username of the user
     * @param oldComic the comic to update
     * @param newComic the new comic to replace the old comic
     * @return The updated comic or null if the comic was not in the collection
     * @throws IOException if there is an error updating the comic
     */
    public Comic updateComic(String username, Comic oldComic, Comic newComic) throws IOException;

    /**
     * Sets the file format adapter to use for importing and exporting the user's
     * personal collection.
     * 
     * @param adapter the file format adapter to use
     */
    public void setImportExportAdapter(ImportExportAdapter adapter);

    /**
     * Imports the user's personal collection from the given file
     * 
     * @param username the username of the user
     * @param filename the file to import the collection from
     * @throws IOException if there is an error importing the collection
     */
    public void importCollection(String username, String filename) throws IOException;

    /**
     * Exports the user's personal collection to the given file
     * 
     * @param username the username of the user
     * @param filename the file to export the collection to
     * @throws IOException if there is an error exporting the collection
     */
    public void exportCollection(String username, String filename) throws IOException;
}
