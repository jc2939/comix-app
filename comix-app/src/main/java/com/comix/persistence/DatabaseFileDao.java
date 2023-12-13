package com.comix.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.comix.model.collection.PersonalCollection;
import com.comix.model.collection.visitor.PersonalCollectionVisitor;
import com.comix.model.comic.Comic;
import com.comix.persistence.json.PersonalCollectionJsonDeserializer;
import com.comix.persistence.json.PersonalCollectionJsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
@Component
public class DatabaseFileDao implements DatabaseDao {
    private PersonalCollection database;
    private ObjectMapper objectMapper;
    private SimpleModule module;
    private static final String FILENAME="data/database.json";
    /**
     * 
     * @param objectMapper
     * @throws IOException
     */
    public DatabaseFileDao(ObjectMapper objectMapper) throws IOException{
        this.objectMapper=objectMapper;
        this.module = new SimpleModule();
        this.module.addSerializer(PersonalCollection.class, new PersonalCollectionJsonSerializer());
        this.module.addDeserializer(PersonalCollection.class, new PersonalCollectionJsonDeserializer());
        this.objectMapper.registerModule(module);

        load();
    }

    private void load() throws IOException {
        database = objectMapper.readValue(new File(FILENAME), PersonalCollection.class);
    }

    public ArrayList<Comic> searchDatabase(PersonalCollectionVisitor visitor) {
        database.accept(visitor);
        return visitor.getResults();
    }
}
