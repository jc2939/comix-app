package com.comix.persistence.xml;

import com.comix.model.collection.PersonalCollection;
import com.comix.model.comic.Comic;
import com.comix.model.comic.GradeDecorator;
import com.comix.model.comic.SlabDecorator;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;

import java.io.IOException;
import java.util.ArrayList;

public class PersonalCollectionXmlSerializer extends StdSerializer<PersonalCollection> {

    public PersonalCollectionXmlSerializer() {
        super(PersonalCollection.class);
    }

    /**
     * The serialize method is overridden to provide custom serialization logic.
     * <p>
     * The method starts an XML object, writes the userName from the
     * personalCollection,
     * starts a "comics" array, iterates through each comic in the
     * personalCollection,
     * writes each comic's fields to an XML object, checks if the comic is an
     * instance
     * of GradeDecorator or SlabDecorator to write the "grade" or "slabbed" fields,
     * ends the comic's XML object, ends the "comics" array, and ends the XML
     * object.
     * </p>
     *
     * @param personalCollection The personalCollection object to serialize.
     * @param jsonGenerator      The JsonGenerator used to write the output...
     *                           casted to ToXmlGenerator
     * @param serializerProvider The SerializerProvider used to access the
     *                           serialization process.
     * @throws IOException If an input or output exception occurred.
     */
    @Override
    public void serialize(PersonalCollection pc, JsonGenerator gen, SerializerProvider provider) throws IOException {
        ToXmlGenerator xmlGen = (ToXmlGenerator) gen;
        xmlGen.writeStartObject();

        xmlGen.writeStringField("userName", pc.getName());
        xmlGen.writeFieldName("comics");
        xmlGen.writeStartArray();
        // Iterate through each comic in the personalCollection
        ArrayList<Comic> comics = pc.getComics();
        for (Comic comic : comics) {
            xmlGen.writeStartObject(); // Start each comic's XML object

            // Serialize ConcreteComic fields
            xmlGen.writeStringField("publisher", comic.getPublisher());
            xmlGen.writeStringField("series", comic.getSeries());
            xmlGen.writeStringField("volume", comic.getVolume());
            xmlGen.writeStringField("issue", comic.getIssue());
            xmlGen.writeStringField("publicationDate", comic.getPublicationDate());
            xmlGen.writeStringField("comicTitle", comic.getComicTitle());
            xmlGen.writeObjectField("creators", comic.getCreators());
            xmlGen.writeObjectField("principleCharacters", comic.getPrincipleCharacters());
            xmlGen.writeStringField("description", comic.getDescription());

            // Check if the comic is an instance of GradeDecorator to write the grade field.
            if (comic instanceof GradeDecorator) {
                GradeDecorator<?> gradedComic = (GradeDecorator<?>) comic;
                xmlGen.writeNumberField("grade", gradedComic.getGrade());
            }

            // Check if the comic is an instance of SlabDecorator to write the slabbed
            // field.
            if (comic instanceof SlabDecorator) {
                SlabDecorator<?> slabbedComic = (SlabDecorator<?>) comic;
                xmlGen.writeBooleanField("slabbed", slabbedComic.getSlabbed());
            }
            Comic concreteComic = comic;
            while (concreteComic.getComic() != null) {
                concreteComic = concreteComic.getComic();
            }
            xmlGen.writeNumberField("value", concreteComic.getValue());

            xmlGen.writeEndObject(); // End each comic's JSON object
        }

        xmlGen.writeEndArray(); // End the comics array
        xmlGen.writeEndObject(); // End the outer JSON object
    }
}
