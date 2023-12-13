package com.comix.persistence.csv;

import java.io.File;
import java.io.IOException;

import com.comix.model.collection.PersonalCollection;
import com.comix.persistence.ImportExportAdapter;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;

public class CSVAdapter implements ImportExportAdapter {

    private CsvMapper csvMapper;
    private SimpleModule module;

    public CSVAdapter() {
        this.csvMapper = new CsvMapper();
        this.module = new SimpleModule();
        // this.module.addSerializer(PersonalCollection.class, new
        // PersonalCollectionCsvSerializer());
        // this.module.addDeserializer(PersonalCollection.class, new
        // PersonalCollectionCsvDeserializer());
        this.csvMapper.registerModule(module);
    }

    @Override
    public PersonalCollection importCollection(String file) {
        try {
            return csvMapper.readValue(new File(file), PersonalCollection.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String exportCollection(PersonalCollection collection) {
        try {
            return csvMapper.writeValueAsString(collection);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
