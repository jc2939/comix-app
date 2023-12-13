package com.comix.persistence;

import com.comix.model.collection.PersonalCollection;

public interface ImportExportAdapter {
    /**
     * Imports a PersonalCollection object from a file.
     * 
     * @param file the file to import from
     * @return the PersonalCollection object
     */
    public PersonalCollection importCollection(String file);

    /**
     * Exports a PersonalCollection object to a file.
     * 
     * @param collection the PersonalCollection object to export
     * @return the file
     */
    public String exportCollection(PersonalCollection collection);
}
