package com.comix.model.comic;

import com.comix.persistence.json.ComicJsonDeserializer;
import com.comix.persistence.json.ComicJsonSerializer;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Object representing a basic comic book,
 * encapsulating comic state and behavior.
 * 
 * @author Alec Kovalczik
 */
@JsonDeserialize(using = ComicJsonDeserializer.class)
@JsonSerialize(using = ComicJsonSerializer.class)
public class ConcreteComic implements Comic {
    private static int nextID = 1;
    // @JsonProperty("comicID")
    private String comicID;
    // @JsonProperty("publisher")
    private String publisher;
    // @JsonProperty("series")
    private String series;
    // @JsonProperty("volume")
    private String volume;
    // @JsonProperty("issue")
    private String issue;
    // @JsonProperty("publicationDate")
    private String publicationDate;
    // @JsonProperty("comicTitle")
    private Optional<String> comicTitle;
    // @JsonProperty("creators")
    private Optional<ArrayList<String>> creators;
    // @JsonProperty("principleCharacters")
    private Optional<ArrayList<String>> principleCharacters;
    // @JsonProperty("description")
    private Optional<String> description;
    // @JsonProperty("value")
    private Optional<Double> value;

    /**
     * Constructor for a ConcreteComic
     * 
     * @param publisher           String representing a publishers
     * @param series              String representing a series
     * @param volume              String representing a volume
     * @param issue               String representing an issue
     * @param publicationDate     Date the comic was published in string form
     * @param comicTitle          Title of the comic (Optional)
     * @param creators            An ArrayList<String> of creators associated with
     *                            the comic (Optional)
     * @param principleCharacters An ArrayList<String> of principle characters in
     *                            the comic (Optional)
     * @param description         A description of the comic (Optional)
     * @param value               The value of the comic (Optional)
     */

    // @JsonCreator
    public ConcreteComic(@JsonProperty("publisher") String publisher,
            @JsonProperty("series") String series,
            @JsonProperty("volume") String volume,
            @JsonProperty("issue") String issue,
            @JsonProperty("publicationDate") String publicationDate,
            @JsonProperty("comicTitle") String comicTitle,
            @JsonProperty("creators") ArrayList<String> creators,
            @JsonProperty("principleCharacters") ArrayList<String> principleCharacters,
            @JsonProperty("description") String description,
            @JsonProperty("value") Double value) {
        setComicID();
        this.publisher = publisher;
        this.series = series;
        this.volume = volume;
        this.issue = issue;
        this.publicationDate = publicationDate;
        this.comicTitle = Optional.ofNullable(comicTitle);
        this.creators = Optional.ofNullable(creators);
        this.principleCharacters = Optional.ofNullable(principleCharacters);
        this.description = Optional.ofNullable(description);
        this.value = Optional.ofNullable(value);
    }

    /**
     * Constructor for a ConcreteComic
     * 
     * @param comicID             String representing this comic's unique ID
     * @param publisher           String representing a publishers
     * @param series              String representing a series
     * @param volume              String representing a volume
     * @param issue               String representing an issue
     * @param publicationDate     Date the comic was published in string form
     * @param comicTitle          Title of the comic (Optional)
     * @param creators            An ArrayList<String> of creators associated with
     *                            the comic (Optional)
     * @param principleCharacters An ArrayList<String> of principle characters in
     *                            the comic (Optional)
     * @param description         A description of the comic (Optional)
     * @param value               The value of the comic (Optional)
     */
    private ConcreteComic(String comicID, String publisher, String series, String volume, String issue,
            String publicationDate, String comicTitle, ArrayList<String> creators,
            ArrayList<String> principleCharacters, String description, Double value) {
        this.comicID = comicID;
        this.publisher = publisher;
        this.series = series;
        this.volume = volume;
        this.issue = issue;
        this.publicationDate = publicationDate;
        this.comicTitle = Optional.ofNullable(comicTitle);
        this.creators = Optional.ofNullable(creators);
        this.principleCharacters = Optional.ofNullable(principleCharacters);
        this.description = Optional.ofNullable(description);
        this.value = Optional.ofNullable(value);
    }

    @Override
    public Comic copy() {
        return new ConcreteComic(getComicID(), getPublisher(), getSeries(), getVolume(), getIssue(),
                getPublicationDate(), getComicTitle(), getCreators(), getPrincipleCharacters(), getDescription(),
                getValue());
    }

    /**
     * Method that gives the comic a unique comicID
     */
    private void setComicID() {
        this.comicID = Integer.toString(nextID);
        nextID++;
    }

    /**
     * Accessor for the comicID
     * 
     * @return comicID
     */
    @Override
    public String getComicID() {
        return comicID;
    }

    /**
     * Accessor for the title of the comic
     * 
     * @return value in Optional comicTitle | null if not defined
     */
    @Override
    public String getComicTitle() {
        try {
            if (comicTitle.get() == "") {
                throw new NoSuchElementException("Empty String");
            }
            return comicTitle.get();
        } catch (NoSuchElementException e) {
            return "untitled";
        }
    }

    /**
     * mutator for the title of the comic
     * 
     * @param comicTitle new String to be passed into the Optional comicTitle | null
     *                   if not defined
     */
    @Override
    public void setComicTitle(String comicTitle) {
        this.comicTitle = Optional.ofNullable(comicTitle);
    }

    /**
     * Accessor for the publisher
     * 
     * @return publisher (String)
     */
    @Override
    public String getPublisher() {
        return publisher;
    }

    /**
     * Mutator for the publisher
     * 
     * @param publisher String representing the new publisher
     */
    @Override
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     * Accessor for the series
     * 
     * @return series (String)
     */
    @Override
    public String getSeries() {
        return series;
    }

    /**
     * Mutator for the series
     * 
     * @param series String representing the new series
     */
    @Override
    public void setSeries(String series) {
        this.series = series;
    }

    /**
     * Accessor for the volume
     * 
     * @return volume (String)
     */
    @Override
    public String getVolume() {
        return volume;
    }

    /**
     * Mutator for the volume
     * 
     * @param volume String representing the new volume
     */
    @Override
    public void setVolume(String volume) {
        this.volume = volume;
    }

    /**
     * Accessor for the issue
     * 
     * @return issue (String)
     */
    @Override
    public String getIssue() {
        return issue;
    }

    /**
     * Mutator for the issue
     * 
     * @param issue String representing the new issue
     */
    @Override
    public void setIssue(String issue) {
        this.issue = issue;
    }

    /**
     * Accessor for the publication date
     * 
     * @return publicationDate (String format)
     */
    @Override
    public String getPublicationDate() {
        return publicationDate;
    }

    /**
     * Mutator for the publication date
     * 
     * @param publicationDate new publication date for the comic (String format)
     */
    @Override
    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    /**
     * Accessor for the creators
     * 
     * @return value in Optional creators (ArrayList<String>) | null if not defined
     */
    @Override
    public ArrayList<String> getCreators() {
        try {
            return creators.get();
        } catch (NoSuchElementException e) {
            return new ArrayList<String>();
        }
    }

    /**
     * mutator for the creators
     * 
     * @param creators new ArrayList<String> to be passed into the Optional creators
     *                 | null if not defined
     */
    @Override
    public void setCreators(ArrayList<String> creators) {
        this.creators = Optional.ofNullable(creators);
    }

    /**
     * Accessor for the principal characters
     * 
     * @return value in Optional principalCharacters (ArrayList<String>) | null if
     *         not defined
     */
    @Override
    public ArrayList<String> getPrincipleCharacters() {
        try {
            return principleCharacters.get();
        } catch (NoSuchElementException e) {
            return new ArrayList<String>();
        }
    }

    /**
     * mutator for the principleCharacters
     * 
     * @param principleCharacters new ArrayList<String> to be passed into the
     *                            Optional principleCharacters | null if not defined
     */
    @Override
    public void setPrincipleCharacters(ArrayList<String> principleCharacters) {
        this.principleCharacters = Optional.ofNullable(principleCharacters);
    }

    /**
     * Accessor for the description
     * 
     * @return value in Optional description (String) | null if not defined
     */
    @Override
    public String getDescription() {
        try {
            return description.get();
        } catch (NoSuchElementException e) {
            return "";
        }
    }

    /**
     * mutator for the description
     * 
     * @param description new String to be passed into the Optional description |
     *                    null if not defined
     */
    @Override
    public void setDescription(String description) {
        this.description = Optional.ofNullable(description);
    }

    /**
     * Accessor for the creators
     * 
     * @return value in Optional creators (ArrayList<String>) | null if not defined
     */
    @Override
    public double getValue() { // need to implement rounding
        try {
            return round(value.get(), 2);
        } catch (NoSuchElementException e) {
            return 0;
        }
    }

    /**
     * mutator for the value
     * 
     * @param value new double to be passed into the Optional value | null if not
     *              defined
     */
    @Override
    public void setValue(double value) {
        this.value = Optional.ofNullable(value);
    }

    /**
     * Accessor for comic being wrapped, this is useful as an endpoint for the
     * decorator stack.
     */
    @Override
    public Comic getComic() {
        return null;
    }

    @Override
    public String toString() {
        return getComicTitle();
    }

    /**
     * Rounding method use to make our doubles conform the format for prices.
     * 
     * @param value  what we're rounding
     * @param places how many places, we'll usually use 2
     * @return the rounded double
     * 
     *         Found at:
     *         https://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
     *         This is the example marked as the best answer under the heading "So,
     *         use this instead"
     */
    private static double round(double value, int places) {
        if (places < 0)
            throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
