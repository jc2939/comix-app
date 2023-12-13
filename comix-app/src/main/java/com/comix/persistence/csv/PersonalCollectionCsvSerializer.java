package com.comix.persistence.csv;

import com.comix.model.collection.PersonalCollection;
import com.comix.model.comic.Comic;
import com.comix.model.comic.GradeDecorator;
import com.comix.model.comic.SlabDecorator;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.dataformat.csv.CsvGenerator;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.IOException;

// The main dev of Jackson wrote a useful dev.to article on how to write custom csv serializers:
// https://cowtowncoder.medium.com/writing-csv-with-jackson-204fdb3c9dac
public class PersonalCollectionCsvSerializer extends StdSerializer<PersonalCollection> {

    public PersonalCollectionCsvSerializer() {
        super(PersonalCollection.class);
    }

    /**
     * The serialize method is overridden to provide custom serialization logic.
     * <p>
     * The method starts a CSV file, writes the userName from the
     * personalCollection,
     * starts a "comics" array, iterates through each comic in the
     * personalCollection,
     * writes each comic's fields to a CSV file, checks if the comic is an
     * instance
     * of GradeDecorator or SlabDecorator to write the "grade" or "slabbed" fields,
     * ends the comic's JSON object, ends the "comics" array, and ends the JSON
     * object.
     * </p>
     *
     * @param personalCollection The personalCollection object to serialize.
     * @param jsonGenerator      The JsonGenerator used to write the output...
     *                           casted to CsvGenerator
     * @param serializerProvider The SerializerProvider used to access the
     *                           serialization process.
     * @throws IOException If an input or output exception occurred.
     */
    @Override
    public void serialize(PersonalCollection value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        CsvGenerator csvGenerator = (CsvGenerator) gen;

        // Define CSV schema based on the Comic class structure
        CsvSchema schema = CsvSchema.builder()
                .addColumn("userName")
                .addColumn("publisher")
                .addColumn("series")
                .addColumn("volume")
                .addColumn("issue")
                .addColumn("publicationDate")
                .addColumn("comicTitle")
                .addColumn("creators")
                .addColumn("principleCharacters")
                .addColumn("description")
                .addColumn("value")
                .addColumn("grade")
                .addColumn("slabbed")
                .build();

        csvGenerator.setSchema(schema);

        // Iterate through each comic in the collection and write a row in the CSV
        for (Comic comic : value.getComics()) {
            // Serialize ConcreteComic fields
            csvGenerator.writeStartObject();
            csvGenerator.writeStringField("userName", value.getName());
            csvGenerator.writeStringField("publisher", comic.getPublisher());
            csvGenerator.writeStringField("series", comic.getSeries());
            csvGenerator.writeStringField("volume", comic.getVolume());
            csvGenerator.writeStringField("issue", comic.getIssue());
            csvGenerator.writeStringField("publicationDate", comic.getPublicationDate());
            csvGenerator.writeStringField("comicTitle", comic.getComicTitle());
            csvGenerator.writeObjectField("creators", comic.getCreators());
            csvGenerator.writeObjectField("principleCharacters", comic.getPrincipleCharacters());
            csvGenerator.writeStringField("description", comic.getDescription());
            csvGenerator.writeNumberField("value", comic.getValue());

            // Check if the comic is an instance of GradeDecorator to write the grade field.
            if (comic instanceof GradeDecorator) {
                GradeDecorator<?> gradedComic = (GradeDecorator<?>) comic;
                csvGenerator.writeNumberField("grade", gradedComic.getGrade());
            }

            // Check if the comic is an instance of SlabDecorator to write the slabbed
            // field.
            if (comic instanceof SlabDecorator) {
                SlabDecorator<?> slabbedComic = (SlabDecorator<?>) comic;
                csvGenerator.writeBooleanField("slabbed", slabbedComic.getSlabbed());
            }

            csvGenerator.writeEndObject();
        }

        csvGenerator.close();
    }
}
