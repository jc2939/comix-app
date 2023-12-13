package com.comix.persistence.json;

import com.comix.model.collection.PersonalCollection;
import com.comix.model.comic.Comic;
import com.comix.model.comic.GradeDecorator;
import com.comix.model.comic.SignatureDecorator;
import com.comix.model.comic.SlabDecorator;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Custom serializer for PersonalCollection class.
 * Extends Jackson's StdSerializer to customize the serialization of a
 * PersonalCollection object to JSON.
 * <p>
 * This serializer outputs a JSON object with a "userName" field and a "comics"
 * array.
 * Each comic object within the "comics" array contains common comic fields as
 * well as
 * optional "grade" and "slabbed" fields if the comic has been graded or slabbed
 * respectively.
 * </p>
 */
public class PersonalCollectionJsonSerializer extends StdSerializer<PersonalCollection> {

    /**
     * Default constructor.
     * This constructor is used to instantiate the serializer without specifying a
     * class.
     */
    public PersonalCollectionJsonSerializer() {
        this(null);
    }

    /**
     * Overloaded constructor.
     * This constructor is used to instantiate the serializer with a specified
     * class.
     *
     * @param t The class type for the serializer.
     */
    public PersonalCollectionJsonSerializer(Class<PersonalCollection> t) {
        super(t);
    }

    /**
     * The serialize method is overridden to provide custom serialization logic.
     * <p>
     * The method starts a JSON object, writes the userName from the
     * personalCollection,
     * starts a "comics" array, iterates through each comic in the
     * personalCollection,
     * writes each comic's fields to a JSON object, checks if the comic is an
     * instance
     * of GradeDecorator or SlabDecorator to write the "grade" or "slabbed" fields,
     * ends the comic's JSON object, ends the "comics" array, and ends the JSON
     * object.
     * </p>
     *
     * @param personalCollection The personalCollection object to serialize.
     * @param jsonGenerator      The JsonGenerator used to write the JSON output.
     * @param serializerProvider The SerializerProvider used to access the
     *                           serialization process.
     * @throws IOException If an input or output exception occurred.
     */
    @Override
    public void serialize(
            PersonalCollection personalCollection,
            JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider)
            throws IOException {

        jsonGenerator.writeStartObject(); // Start the outer JSON object
        jsonGenerator.writeStringField("userName", personalCollection.getName()); // Write the userName field

        jsonGenerator.writeArrayFieldStart("comics"); // Start the comics array

        // Iterate through each comic in the personalCollection
        ArrayList<Comic> comics = personalCollection.getComics();
        for (Comic comic : comics) {
            jsonGenerator.writeStartObject(); // Start each comic's JSON object

            // Serialize ConcreteComic fields
            jsonGenerator.writeStringField("publisher", comic.getPublisher());
            jsonGenerator.writeStringField("series", comic.getSeries());
            jsonGenerator.writeStringField("volume", comic.getVolume());
            jsonGenerator.writeStringField("issue", comic.getIssue());
            jsonGenerator.writeStringField("publicationDate", comic.getPublicationDate());
            jsonGenerator.writeStringField("comicTitle", comic.getComicTitle());
            jsonGenerator.writeObjectField("creators", comic.getCreators());
            jsonGenerator.writeObjectField("principleCharacters", comic.getPrincipleCharacters());
            jsonGenerator.writeStringField("description", comic.getDescription());

            // Check if the comic is an instance of GradeDecorator to write the grade field.
            if (comic instanceof GradeDecorator) {
                GradeDecorator<?> gradedComic = (GradeDecorator<?>) comic;
                jsonGenerator.writeNumberField("grade", gradedComic.getGrade());
            }

            // Check if the comic is an instance of SlabDecorator to write the slabbed
            // field.
            if (comic instanceof SlabDecorator) {
                SlabDecorator<?> slabbedComic = (SlabDecorator<?>) comic;
                jsonGenerator.writeBooleanField("slabbed", slabbedComic.getSlabbed());
            }

            // Check if the comic is an instance of SignatureDecorator and write the
            // signatures as an array
            if (comic instanceof SignatureDecorator) {
                SignatureDecorator<?> signedComic = (SignatureDecorator<?>) comic;
                jsonGenerator.writeArrayFieldStart("signatures");
                Map<String, Boolean> signatures = signedComic.getSignatures();
                for (String signature : signatures.keySet()) {
                    jsonGenerator.writeStartObject();
                    jsonGenerator.writeStringField("signature", signature);
                    jsonGenerator.writeBooleanField("authentication", signatures.get(signature));
                    jsonGenerator.writeEndObject();
                }
                jsonGenerator.writeEndArray();
            }

            // loop through the comic's decorators to get the concrete comic and write its
            // value
            Comic concreteComic = comic;
            while (concreteComic.getComic() != null) {
                concreteComic = concreteComic.getComic();
            }
            jsonGenerator.writeNumberField("value", concreteComic.getValue());

            jsonGenerator.writeEndObject(); // End each comic's JSON object
        }

        jsonGenerator.writeEndArray(); // End the comics array
        jsonGenerator.writeEndObject(); // End the outer JSON object
    }
}
