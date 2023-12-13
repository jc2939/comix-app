package com.comix.persistence.xml;

import com.comix.model.collection.PersonalCollection;
import com.comix.model.comic.Comic;
import com.comix.model.comic.ConcreteComic;
import com.comix.model.comic.GradeDecorator;
import com.comix.model.comic.SlabDecorator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Custom deserializer for the PersonalCollection class.
 * Extends Jackson's StdDeserializer to customize the deserialization of an XML
 * object to a PersonalCollection object.
 * <p>
 * This deserializer reads an XML object with a "userName" field and a "comics"
 * array,
 * and constructs a PersonalCollection object with corresponding data.
 * Each comic object within the "comics" array is deserialized to a
 * ConcreteComic, GradeDecorator, or SlabDecorator object,
 * depending on the fields present in the comic object.
 * </p>
 */
public class PersonalCollectionXmlDeserializer extends StdDeserializer<PersonalCollection> {

    /**
     * Default constructor.
     * This constructor is used to instantiate the deserializer without specifying a
     * class.
     */
    public PersonalCollectionXmlDeserializer() {
        this(null);
    }

    /**
     * Overloaded constructor.
     * This constructor is used to instantiate the deserializer with a specified
     * class.
     *
     * @param vc The class type for the deserializer.
     */
    public PersonalCollectionXmlDeserializer(Class<?> vc) {
        super(vc);
    }

    /**
     * The deserialize method is overridden to provide custom deserialization logic.
     * <p>
     * The method reads the userName from the XML object,
     * iterates through each comic object in the "comics" array,
     * deserializes each comic object to a ConcreteComic, GradeDecorator, or
     * SlabDecorator object,
     * depending on the fields present in the comic object,
     * and adds each comic object to the PersonalCollection object.
     * </p>
     *
     * @param jsonParser The JsonParser used to read the input... casted to
     *                   XmlMapper
     * @param context    The DeserializationContext used to access the
     *                   deserialization process.
     * @return The PersonalCollection object constructed from the XML object.
     * @throws IOException If an input or output exception occurred.
     */
    @Override
    public PersonalCollection deserialize(
            JsonParser jsonParser,
            DeserializationContext deserializationContext)
            throws IOException {

        XmlMapper xmlMapper = (XmlMapper) jsonParser.getCodec();
        JsonNode node = xmlMapper.readTree(jsonParser);
        String userName = node.get("userName").asText();
        PersonalCollection personalCollection = new PersonalCollection(userName);

        JsonNode comicsNode = node.get("comics");
        for (JsonNode comicNode : comicsNode) {
            // Deserialize ConcreteComic fields
            String publisher = comicNode.get("publisher").asText();
            String series = comicNode.get("series").asText();
            String volume = comicNode.get("volume").asText();
            String issue = comicNode.get("issue").asText();
            String publicationDate = comicNode.get("publicationDate").asText();
            String comicTitle = comicNode.get("comicTitle").asText();

            TypeReference<ArrayList<String>> typeRef = new TypeReference<ArrayList<String>>() {
            };
            ArrayList<String> creators = xmlMapper.convertValue(comicNode.get("creators"), typeRef);
            ArrayList<String> principleCharacters = xmlMapper.convertValue(comicNode.get("principleCharacters"),
                    typeRef);

            String description = comicNode.get("description").asText();
            Double value = comicNode.get("value").asDouble();

            ConcreteComic comic = new ConcreteComic(
                    publisher, series, volume, issue,
                    publicationDate, comicTitle, creators,
                    principleCharacters, description, value);

            Comic decoratedComic = comic; // initialize with the base comic

            // Check if the comic is graded
            if (comicNode.has("grade")) {
                int grade = comicNode.get("grade").asInt();
                try {
                    GradeDecorator<ConcreteComic> gradedComic = new GradeDecorator<>(comic, grade);
                    decoratedComic = gradedComic; // update decoratedComic to hold the graded version
                } catch (Exception e) {
                    e.printStackTrace(); // Handle exception
                }
            }

            // Check if the comic is slabbed, and if it's graded before being slabbed
            if (comicNode.has("slabbed") && decoratedComic instanceof GradeDecorator) {
                boolean slabbed = comicNode.get("slabbed").asBoolean();
                if (slabbed) {
                    try {
                        SlabDecorator<Comic> slabbedComic = new SlabDecorator<>(decoratedComic, true);
                        decoratedComic = slabbedComic; // update decoratedComic to hold the slabbed version
                    } catch (Exception e) {
                        e.printStackTrace(); // Handle exception
                    }
                }
            }

            personalCollection.addComic(decoratedComic); // add the final version of decoratedComic to the collection
        }

        return personalCollection;
    }
}
