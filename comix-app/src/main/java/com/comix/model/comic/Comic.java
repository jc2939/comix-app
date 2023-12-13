package com.comix.model.comic;

import java.util.ArrayList;

import com.comix.persistence.json.ComicJsonDeserializer;
import com.comix.persistence.json.ComicJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Interface that is used to represent a comic of any form.
 * Also provides all comics with some basic functionality they
 * all should have.
 * 
 * @author Alec Kovalczik
 */
@JsonDeserialize(using = ComicJsonDeserializer.class)
@JsonSerialize(using = ComicJsonSerializer.class)
public interface Comic {

    /**
     * Create a deep copy of the comic, regardless of type
     * 
     * @return Comic copy
     */
    public Comic copy();

    /**
     * Accessor for a Comic's comicID
     * 
     * @return the Comic's ID
     */
    public String getComicID();

    /**
     * Accessor for a Comic's comicTitle
     * 
     * @return A String representing the Comic's title
     */
    public String getComicTitle();

    /**
     * Mutator for a Comic's comicTitle
     * 
     * @param comicTitle A string representing the Comic's new title
     */
    public void setComicTitle(String comicTitle);

    /**
     * Accessor for a Comic's publisher
     * 
     * @return a String reprsenting the Comic's publisher
     */
    public String getPublisher();

    /**
     * Mutator for a Comic's publisher
     * 
     * @param publisher A string representing the Comic's new publisher
     */
    public void setPublisher(String publisher);

    /**
     * Accessor for a Comic's series
     * 
     * @return A String representing the Comic's series
     */
    public String getSeries();

    /**
     * Mutator for a Comic's series
     * 
     * @param series A string representing the Comic's new series
     */
    public void setSeries(String series);

    /**
     * Accessor for a Comic's volume
     * 
     * @return A String representing the Comic's volume
     */
    public String getVolume();

    /**
     * Mutator for a Comic's volume
     * 
     * @param volume A string representing the Comic's new volume
     */
    public void setVolume(String volume);

    /**
     * Accessor for a Comic's issue
     * 
     * @return A String representing the Comic's issue number
     */
    public String getIssue();

    /**
     * Mutator for a Comic's issue
     * 
     * @param issue A string representing the Comic's new issue number
     */
    public void setIssue(String issue);

    /**
     * Accessor for a Comic's publicationDate
     * 
     * @return A String representing the Comic's publicationDate
     */
    public String getPublicationDate();

    /**
     * Mutator for a Comic's publicationDate
     * 
     * @param publicationDate A String representing the date the comic was published
     */
    public void setPublicationDate(String publicationDate);

    /**
     * Accessor for a Comic's creators
     * 
     * @return An ArrayList of String's representing the list of creators associated
     *         with the Comic
     */
    public ArrayList<String> getCreators();

    /**
     * Mutator for a Comic's creators
     * 
     * @param creators New ArrayList storing strings that represent the creators
     *                 associated with the Comic
     */
    public void setCreators(ArrayList<String> creators);

    /**
     * Accessor for a Comic's principleCharacters
     * 
     * @return An ArrayList of String's representing the list of principleCharacters
     *         in the Comic
     */
    public ArrayList<String> getPrincipleCharacters();

    /**
     * Mutator for a Comic's principleCharacters
     * 
     * @param principleCharacters New ArrayList storing strings that represent
     *                            principleCharacters for the Comic
     */
    public void setPrincipleCharacters(ArrayList<String> principleCharacters);

    /**
     * Accessor for a Comic's description
     * 
     * @return A String representing the Comic's description
     */
    public String getDescription();

    /**
     * Mutator for a Comic's description
     * 
     * @param description String representing the new description for the Comic
     */
    public void setDescription(String description);

    /**
     * Accessor for a Comic's value (including alterations made by grading,
     * slabbing, etc)
     * 
     * @return A double representing the Comic's value (including alterations from
     *         decorators)
     */
    public double getValue();

    /**
     * Mutator for a Comic's value, changes it at the ConcreteComic level
     * 
     * @param value double value representing the new value for the ConcreteComic
     */
    public void setValue(double value);

    /**
     * Accessor for a Comic's Comic.
     * This will be null for ConcreteComics, and
     * the Comic it wraps if it's a type of ComicDecorator
     * 
     * @return A Comic object that's wrapped by the current Comic, or null if the
     *         current Comic is a ConcreteComic
     */
    public Comic getComic();

}
