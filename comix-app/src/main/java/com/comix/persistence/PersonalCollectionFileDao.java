package com.comix.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import com.comix.model.collection.Collection;
import com.comix.model.collection.PersonalCollection;
import com.comix.model.collection.visitor.CollectionVisitor;
import com.comix.model.comic.Comic;
import com.comix.persistence.json.JSONAdapter;
import com.comix.persistence.json.PersonalCollectionJsonDeserializer;
import com.comix.persistence.json.PersonalCollectionJsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Component
public class PersonalCollectionFileDao implements PersonalCollectionDao {
    private ImportExportAdapter importExportAdapter;
    private Map<String, PersonalCollection> personalCollections;
    private ObjectMapper objectMapper;
    private SimpleModule module;

    private static final String FILENAME = "data/personalCollections.json";

    /**
     * Creates a new PersonalCollectionFileDao
     * 
     * @param objectMapper the ObjectMapper to use for serialization/deserialization
     */
    public PersonalCollectionFileDao(ObjectMapper objectMapper) throws IOException {
        this.importExportAdapter = new JSONAdapter(objectMapper);
        this.objectMapper = objectMapper;
        this.module = new SimpleModule();
        this.module.addSerializer(PersonalCollection.class, new PersonalCollectionJsonSerializer());
        this.module.addDeserializer(PersonalCollection.class, new PersonalCollectionJsonDeserializer());
        this.objectMapper.registerModule(module);
        load();
    }

    /**
     * Loads the personal collections from the file
     */
    private void load() throws IOException {
        personalCollections = new TreeMap<String, PersonalCollection>();

        // Deserializes the JSON objects from the file into an array of collections
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        PersonalCollection[] collectionArray = objectMapper.readValue(new File(FILENAME), PersonalCollection[].class);

        // Add each collection to the tree map
        for (PersonalCollection collection : collectionArray) {
            // if the username isn't already in the tree map, add it
            if (!personalCollections.containsKey(collection.getName())) {
                personalCollections.put(collection.getName(), (PersonalCollection) collection);
            }
        }
    }

    /**
     * Saves the personal collections to the file
     */
    private void save() throws IOException {
        objectMapper.writeValue(new File(FILENAME), personalCollections.values());
    }

    @Override
    public Collection createCollection(String username) throws IOException {
        synchronized (personalCollections) {
            if (!personalCollections.containsKey(username)) {
                personalCollections.put(username, new PersonalCollection(username));
            }

            save();
            return personalCollections.get(username);
        }
    }

    @Override
    public Collection getCollection(String username) {
        synchronized (personalCollections) {
            if (personalCollections.containsKey(username))
                return personalCollections.get(username);
            else
                return null;
        }
    }

    @Override
    public ArrayList<Comic> searchCollection(String username, CollectionVisitor visitor) {
        synchronized (personalCollections) {
            if (!personalCollections.containsKey(username)) {
                return null;
            }
            personalCollections.get(username).accept(visitor);
            ArrayList<Comic> searchResult = visitor.getResults();
            return searchResult;
        }
    }

    @Override
    public Comic addComic(String username, Comic comic) throws IOException {
        synchronized (personalCollections) {
            if (personalCollections.containsKey(username)) {
                personalCollections.get(username).addComic(comic);
                save();
                return comic;
            } else {
                return null;
            }
        }
    }

    @Override
    public boolean removeComic(String username, Comic comic) throws IOException {
        synchronized (personalCollections) {
            if (personalCollections.containsKey(username)) {
                System.out.println(personalCollections.get(username).getComicCount());
                personalCollections.get(username).removeComic(comic);
                save();
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public Comic updateComic(String username, Comic oldComic, Comic newComic) throws IOException {
        synchronized (personalCollections) {
            if (personalCollections.containsKey(username)) {
                personalCollections.get(username).removeComic(oldComic);
                personalCollections.get(username).addComic(newComic);
                save();
                return newComic;
            } else {
                return null;
            }
        }
    }

    @Override
    public void setImportExportAdapter(ImportExportAdapter adapter) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setImportExportAdapter'");
    }

    @Override
    public void importCollection(String username, String filename) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'importCollection'");
    }

    @Override
    public void exportCollection(String username, String filename) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'exportCollection'");
    }
}