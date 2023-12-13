package com.comix.persistence.xml;

import com.comix.model.collection.PersonalCollection;
import com.comix.persistence.ImportExportAdapter;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.File;
import java.io.IOException;

public class XMLAdapter implements ImportExportAdapter {

    private XmlMapper xmlMapper;
    private SimpleModule module;

    public XMLAdapter() {
        this.xmlMapper = new XmlMapper();
        this.module = new SimpleModule();
        this.module.addSerializer(PersonalCollection.class, new PersonalCollectionXmlSerializer());
        this.module.addDeserializer(PersonalCollection.class, new PersonalCollectionXmlDeserializer());
        this.xmlMapper.registerModule(module);
    }

    @Override
    public PersonalCollection importCollection(String file) {
        try {
            return xmlMapper.readValue(new File(file), PersonalCollection.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String exportCollection(PersonalCollection collection) {
        try {
            return xmlMapper.writeValueAsString(collection);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
