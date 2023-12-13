package com.comix.persistence.json;

import com.comix.model.collection.PersonalCollection;
import com.comix.persistence.ImportExportAdapter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.File;
import java.io.IOException;

public class JSONAdapter implements ImportExportAdapter {

    private ObjectMapper objectMapper;
    private SimpleModule module;

    public JSONAdapter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.module = new SimpleModule();
        this.module.addSerializer(PersonalCollection.class, new PersonalCollectionJsonSerializer());
        this.module.addDeserializer(PersonalCollection.class, new PersonalCollectionJsonDeserializer());
        this.objectMapper.registerModule(module);
    }

    @Override
    public PersonalCollection importCollection(String file) {
        try {
            // Deserialize the JSON file into a PersonalCollection object
            return objectMapper.readValue(new File(file), PersonalCollection.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null; // or handle the exception (we can replace this with whatever error handling we
                         // decide on)
        }
    }

    @Override
    public String exportCollection(PersonalCollection collection) {
        try {
            // Serialize the PersonalCollection object into a JSON string
            return objectMapper.writeValueAsString(collection);
        } catch (IOException e) {
            e.printStackTrace();
            return null; // or handle the exception (we can replace this with whatever error handling we
                         // decide on)
        }
    }

}
