package com.comix.persistence.csv;

import com.comix.model.collection.PersonalCollection;
import com.comix.model.comic.Comic;
import com.comix.model.comic.ConcreteComic;
import com.comix.model.comic.GradeDecorator;
import com.comix.model.comic.SlabDecorator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Custom deserializer for the PersonalCollection class.
 * Extends Jackson's StdDeserializer to customize the deserialization of an CSV
 * object to a PersonalCollection object.
 * <p>
 * This deserializer reads a CSV object with a "userName" field and a "comics"
 * array,
 * and constructs a PersonalCollection object with corresponding data.
 * Each comic object within the "comics" array is deserialized to a
 * ConcreteComic, GradeDecorator, or SlabDecorator object,
 * depending on the fields present in the comic object.
 * </p>
 * 
 * The main dev of Jackson wrote a useful dev.to article on how to write custom
 * csv deserializers:
 * https://cowtowncoder.medium.com/reading-csv-with-jackson-c4e74a15ddc1
 */
public class PersonalCollectionCsvDeserializer extends StdDeserializer<PersonalCollection> {

    /**
     * Default constructor.
     * This constructor is used to instantiate the deserializer without specifying a
     * class.
     */
    public PersonalCollectionCsvDeserializer() {
        this(null);
    }

    /**
     * Overloaded constructor.
     * This constructor is used to instantiate the deserializer with a specified
     * class.
     *
     * @param vc The class type for the deserializer.
     */
    public PersonalCollectionCsvDeserializer(Class<?> vc) {
        super(vc);
    }

    /**
     * The deserialize method is overridden to provide custom deserialization logic.
     * <p>
     * The method reads the userName from the CSV object,
     * iterates through each comic in the CSV file,
     * deserializes each comic object to a ConcreteComic, GradeDecorator, or
     * SlabDecorator object,
     * depending on the fields present in the comic object,
     * and adds each comic object to the PersonalCollection object.
     * </p>
     *
     * @param jsonParser The JsonParser used to read the input... casted to
     *                   CsvMapper
     * @param context    The DeserializationContext used to access the
     *                   deserialization process.
     * @return The PersonalCollection object constructed from the CSV object.
     * @throws IOException If an input or output exception occurred.
     */
    @Override
    public PersonalCollection deserialize(
            JsonParser jsonParser,
            DeserializationContext deserializationContext)
            throws IOException {

        // the csv header looks like this:
        // userName, publisher, series, volume, issue, publicationDate, comicTitle,
        // creators, principleCharacters, description, value, grade, slabbed
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema schema = csvMapper.schemaFor(Comic.class).withHeader();
        MappingIterator<Map<String, String>> it = csvMapper.readerFor(Map.class).with(schema).readValues(jsonParser);

        // Read the userName from the CSV object
        Map<String, String> csvObject = it.next();
        String userName = csvObject.get("userName");
        PersonalCollection personalCollection = new PersonalCollection(userName);

        // Iterate through each comic in the CSV file
        while (it.hasNext()) {
            csvObject = it.next();

            Comic comic = deserializeCsvRow(csvObject, personalCollection);

            // Add the comic object to the PersonalCollection object
            personalCollection.addComic(comic);
        }

        return personalCollection;
    }

    /**
     * Deserializes a CSV row to a Comic object.
     * <p>
     * The method deserializes a CSV row to a ConcreteComic, GradeDecorator, or
     * SlabDecorator object,
     * depending on the fields present in the CSV row.
     * </p>
     *
     * @param csvObject          The CSV row to deserialize.
     * @param personalCollection The PersonalCollection object to add the comic to.
     * @return The Comic object constructed from the CSV row.
     */
    private Comic deserializeCsvRow(Map<String, String> csvObject, PersonalCollection personalCollection) {
        // Deserialize ConcreteComic fields
        String publisher = csvObject.get("publisher");
        String series = csvObject.get("series");
        String volume = csvObject.get("volume");
        String issue = csvObject.get("issue");
        String publicationDate = csvObject.get("publicationDate");
        String comicTitle = csvObject.get("comicTitle");

        // Deserialize creators (separated by semi-colons)
        ArrayList<String> creators = new ArrayList<String>();
        String creatorsString = csvObject.get("creators");
        String[] creatorsArray = creatorsString.split(";");
        for (String creator : creatorsArray) {
            creators.add(creator);
        }

        // Deserialize principleCharacters (separated by semi-colons)
        ArrayList<String> principleCharacters = new ArrayList<String>();
        String principleCharactersString = csvObject.get("principleCharacters");
        String[] principleCharactersArray = principleCharactersString.split(";");
        for (String principleCharacter : principleCharactersArray) {
            principleCharacters.add(principleCharacter);
        }

        String description = csvObject.get("description");
        Double value = Double.parseDouble(csvObject.get("value"));

        // Deserialize GradeDecorator fields
        int grade = Integer.parseInt(csvObject.get("grade"));

        // Deserialize SlabDecorator fields
        Boolean slabbed = Boolean.parseBoolean(csvObject.get("slabbed"));

        // Instantiate the concrete comic
        ConcreteComic comic = new ConcreteComic(
                publisher, series, volume, issue,
                publicationDate, comicTitle, creators,
                principleCharacters, description, value);

        Comic decoratedComic = comic; // initialize with the base comic

        // Check if the comic is graded
        if (grade != 0) {
            try {
                GradeDecorator<ConcreteComic> gradedComic = new GradeDecorator<>(comic, grade);
                decoratedComic = gradedComic; // update comic to hold the graded version
            } catch (Exception e) {
                e.printStackTrace(); // Handle exception
            }
        }

        // Check if the comic is slabbed, and if it's graded before being slabbed
        if (slabbed && decoratedComic instanceof GradeDecorator) {
            try {
                SlabDecorator<Comic> slabbedComic = new SlabDecorator<>(decoratedComic, true);
                decoratedComic = slabbedComic; // update comic to hold the slabbed version
            } catch (Exception e) {
                e.printStackTrace(); // Handle exception
            }
        }

        return decoratedComic;
    }
}
