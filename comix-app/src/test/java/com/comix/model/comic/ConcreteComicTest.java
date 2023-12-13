package com.comix.model.comic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the ConcreteComic class
 * 
 * @author Alec Kovalczik
 */
@Tag("Model-Tier")
public class ConcreteComicTest {
    private String title;
    private String publisher;
    private String series;
    private String volume;
    private String issue;
    private String publicationDate;
    private ArrayList<String> creators;
    private ArrayList<String> principleCharacters;
    private String description;
    private double value;
    private Comic setupComicDefualt;
    private Comic setupComic;

    @BeforeEach
    public void setup(){
        publisher = "publisher";
        series = "series";
        volume = "volume";
        issue = "issue";

        creators = new ArrayList<String>();
        creators.add("Alec");
        creators.add("Angela");
        creators.add("Delaney");
        
        principleCharacters = new ArrayList<>();
        principleCharacters.add("Bobby");

        title = "Swen 262";
        publicationDate = "Sep 16, 2003";
        description = "Bobby is the main character :)";
        value = 25.00;

        setupComicDefualt = new ConcreteComic(publisher, series, volume, issue, publicationDate, null, null, null, null, null);
        setupComic = new ConcreteComic(publisher, series, volume, issue, publicationDate, title, creators, principleCharacters, description, value);

    }

    @Test 
    public void testBasicConstructor(){
        // Setup
        
        // Invoke
        ConcreteComic basicComic = new ConcreteComic(publisher, series, volume, issue, publicationDate, null, null, null, null, null);

        // Analyze
        assertEquals(publisher, basicComic.getPublisher(), "Publisher in constructor isn't right");
        assertEquals(series, basicComic.getSeries(), "Series in constructor isn't right");
        assertEquals(volume, basicComic.getVolume(), "Volume in constructor isn't right");
        assertEquals(issue, basicComic.getIssue(), "Issue in constructor isn't right");
        assertEquals(publicationDate, basicComic.getPublicationDate(), "PublicationDate in constructor isn't right");

    }

    @Test 
    public void testConstructorWithValue(){
        // Setup
        
        // Invoke
        ConcreteComic ComicWithValue = new ConcreteComic(publisher, series, volume, issue, publicationDate, null, null, null, null, value);

        // Analyze
        assertEquals(publisher, ComicWithValue.getPublisher(), "Publisher in constructor isn't right");
        assertEquals(series, ComicWithValue.getSeries(), "Series in constructor isn't right");
        assertEquals(volume, ComicWithValue.getVolume(), "Volume in constructor isn't right");
        assertEquals(issue, ComicWithValue.getIssue(), "Issue in constructor isn't right");
        assertEquals(publicationDate, ComicWithValue.getPublicationDate(), "PublicationDate in constructor isn't right");
        assertEquals("untitled", ComicWithValue.getComicTitle(), "title in constructor isn't right");
        assertEquals(new ArrayList<String>(), ComicWithValue.getCreators(), "creators in constructor isn't right");
        assertEquals(new ArrayList<String>(), ComicWithValue.getPrincipleCharacters(), "principleCharacters in constructor isn't right");
        assertEquals("", ComicWithValue.getDescription(), "descriptions in constructor isn't right");
        assertEquals(value, ComicWithValue.getValue(), "value in constructor isn't right");

    }

    @Test 
    public void testConstructorWithEverything(){
        // Setup
        
        // Invoke
        ConcreteComic FullComic = new ConcreteComic(publisher, series, volume, issue, publicationDate, title, creators, principleCharacters, description, value);

        // Analyze
        assertEquals(publisher, FullComic.getPublisher(), "Publisher in constructor isn't right");
        assertEquals(series, FullComic.getSeries(), "Series in constructor isn't right");
        assertEquals(volume, FullComic.getVolume(), "Volume in constructor isn't right");
        assertEquals(issue, FullComic.getIssue(), "Issue in constructor isn't right");
        assertEquals(publicationDate, FullComic.getPublicationDate(), "publicationDate in constructor isn't right");
        assertEquals(title, FullComic.getComicTitle(), "title in constructor isn't right");
        assertEquals(creators, FullComic.getCreators(), "creators in constructor isn't right");
        assertEquals(principleCharacters, FullComic.getPrincipleCharacters(), "principleCharacters in constructor isn't right");
        assertEquals(description, FullComic.getDescription(), "descriptions in constructor isn't right");
        assertEquals(value, FullComic.getValue(), "value in constructor isn't right");
    }

    @Test
    public void testGetComicID(){
        // Setup

        // Invoke

        String comic2ID = setupComic.getComicID();
        String comic1ID = setupComicDefualt.getComicID();
        String comic1IDPlusOne = Integer.toString(Integer.parseInt(comic1ID) + 1);
        // Analyze
        assertNotEquals(comic1ID, comic2ID, "Comics have the same id, they are not unique");
        assertEquals(comic1IDPlusOne, comic2ID, "Each comic is not one ID more than the last");
    }    

    @Test
    public void testGetPublisher(){
        // Setup
        
        // Invoke
        String expected = publisher;
        String result = setupComic.getPublisher();
        // Analyze
        assertEquals(expected, result, "Didn't get publisher properly"); 
    }

    @Test 
    public void testSetPublisher(){
        // Setup

        // Invoke
        String expected = "New";
        setupComic.setPublisher(expected);
        String result = setupComic.getPublisher();
        // Analyze
        assertEquals(expected, result, "setPublisher didn't change the publisher properly");
    }

    @Test
    public void testGetSeries(){
        // Setup
        
        // Invoke
        String expected = series;
        String result = setupComic.getSeries();
        // Analyze
        assertEquals(expected, result, "Didn't get series properly"); 
    }

    @Test 
    public void testSetSeries(){
        // Setup

        // Invoke
        String expected = "New";
        setupComic.setSeries(expected);
        String result = setupComic.getSeries();
        // Analyze
        assertEquals(expected, result, "setSeries didn't change the series properly");
    }

    @Test
    public void testGetVolume(){
        // Setup
        
        // Invoke
        String expected = volume;
        String result = setupComic.getVolume();
        // Analyze
        assertEquals(expected, result, "Didn't get volume properly"); 
    }

    @Test 
    public void testSetVolume(){
        // Setup

        // Invoke
        String expected = "New";
        setupComic.setVolume(expected);
        String result = setupComic.getVolume();
        // Analyze
        assertEquals(expected, result, "setVolume didn't change the volumeIssue");
    }

    @Test
    public void testGetIssue(){
        // Setup
        
        // Invoke
        String expected = issue;
        String result = setupComic.getIssue();
        // Analyze
        assertEquals(expected, result, "Didn't get issue properly"); 
    }

    @Test 
    public void testSetIssue(){
        // Setup

        // Invoke
        String expected = "New";
        setupComic.setIssue(expected);
        String result = setupComic.getIssue();
        // Analyze
        assertEquals(expected, result, "setIssue didn't change the issue properly");
    }

    @Test
    public void testGetPublicationDate(){
        // Setup
        
        // Invoke
        String expected = publicationDate;
        String result = setupComic.getPublicationDate();
        // Analyze
        assertEquals(expected, result, "Didn't get publicationDate properly"); 
    }

    @Test 
    public void testSetPublicationDate(){
        // Setup

        // Invoke
        String expected = "Oct 22, 2023";
        setupComic.setPublicationDate(expected);
        String result = setupComic.getPublicationDate();
        // Analyze
        assertEquals(expected, result, "setPublicationDate didn't change the publicationDate properly");
    }

    @Test
    public void testGetComicTitle(){
        // Setup

        // Invoke
        String result1 = setupComicDefualt.getComicTitle();
        String result2 = setupComic.getComicTitle();
        String expected1 = "untitled";
        String expected2 = title;
        // Analyze
        assertEquals(expected1, result1, "getTitle is not returning untitle by default");
        assertEquals(expected2, result2, "getTitle is not returning the title when specified");
    }

    @Test
    public void testSetComicTitle(){
        // Setup

        // Invoke
        String expected1 = "Title";
        String expected2 = "New Title";
        setupComicDefualt.setComicTitle(expected1);
        setupComic.setComicTitle(expected2);
        String result1 = setupComicDefualt.getComicTitle();
        String result2 = setupComic.getComicTitle();
        // Analyze
        assertEquals(expected1, result1, "The new title didn't replace the null title");
        assertEquals(expected2, result2, "The new title didn't replace the old title");
    }

    @Test
    public void testGetCreators(){
        // Setup

        // Invoke
        ArrayList<String> result1 = setupComicDefualt.getCreators();
        ArrayList<String> result2 = setupComic.getCreators();
        ArrayList<String> expected1 = new ArrayList<>();
        ArrayList<String> expected2 = creators;
        // Analyze
        assertEquals(expected1, result1, "getCreators is not returning null by default");
        assertEquals(expected2, result2, "getCreators is not returning the creators when specified");
    }

    @Test
    public void testSetCreators(){
        // Setup

        // Invoke
        ArrayList<String> expected = new ArrayList<>(2);
        expected.add("Alec");
        expected.add("The Team");
        setupComicDefualt.setCreators(expected);
        setupComic.setCreators(expected);
        ArrayList<String> result1 = setupComicDefualt.getCreators();
        ArrayList<String> result2 = setupComic.getCreators();
        // Analyze
        assertEquals(expected, result1, "The new creators didn't replace the null creators");
        assertEquals(expected, result2, "The new creators didn't replace the old creators");
    }
    
    @Test
    public void testGetPrincipleCharacters(){
        // Setup

        // Invoke
        ArrayList<String> result1 = setupComicDefualt.getPrincipleCharacters();
        ArrayList<String> result2 = setupComic.getPrincipleCharacters();
        ArrayList<String> expected1 = new ArrayList<>();
        ArrayList<String> expected2 = principleCharacters;
        // Analyze
        assertEquals(expected1, result1, "getPrincipleCharacters is not returning null by default");
        assertEquals(expected2, result2, "getPrincipleCharacters is not returning the princpleCharacters when specified");
    }

    @Test
    public void testSetPrincipleCharacters(){
        // Setup

        // Invoke
        ArrayList<String> expected = new ArrayList<>(2);
        expected.add("Alec");
        expected.add("The Team");
        setupComicDefualt.setPrincipleCharacters(expected);
        setupComic.setPrincipleCharacters(expected);
        ArrayList<String> result1 = setupComicDefualt.getPrincipleCharacters();
        ArrayList<String> result2 = setupComic.getPrincipleCharacters();
        // Analyze
        assertEquals(expected, result1, "The new principleCharacters didn't replace the null principleCharacters");
        assertEquals(expected, result2, "The new principleCharacters didn't replace the old principleCharacters");
    }

    @Test
    public void testGetDescription(){
        // Setup

        // Invoke
        String result1 = setupComicDefualt.getDescription();
        String result2 = setupComic.getDescription();
        String expected1 = "";
        String expected2 = description;
        // Analyze
        assertEquals(expected1, result1, "getDescription is not returning null by default");
        assertEquals(expected2, result2, "getDescription is not returning the description when specified");
    }

    @Test
    public void testSetDescription(){
        // Setup

        // Invoke
        String expected1 = "description";
        String expected2 = "New description";
        setupComicDefualt.setDescription(expected1);
        setupComic.setDescription(expected2);
        String result1 = setupComicDefualt.getDescription();
        String result2 = setupComic.getDescription();
        // Analyze
        assertEquals(expected1, result1, "The new title didn't replace the null title");
        assertEquals(expected2, result2, "The new title didn't replace the old title");
    }

    @Test
    public void testGetValue(){
        // Setup

        // Invoke
        double result1 = setupComicDefualt.getValue();
        double result2 = setupComic.getValue();
        double expected1 = 0;
        double expected2 = value;
        // Analyze
        assertEquals(expected1, result1, "getValue is not returning 0 by default");
        assertEquals(expected2, result2, "getValue is not returning the value when specified");
    }

    @Test
    public void testSetValue(){
        // Setup

        // Invoke
        double expected1 = 10.0;
        double expected2 = 15.5;
        setupComicDefualt.setValue(expected1);
        setupComic.setValue(expected2);
        double result1 = setupComicDefualt.getValue();
        double result2 = setupComic.getValue();
        // Analyze
        assertEquals(round(expected1, 2), result1, "The new value didn't replace the null value");
        assertEquals(round(expected2, 2), result2, "The new value didn't replace the old value");
    }

    /**
     * Rounding method use to make our doubles conform the format for prices.
     * @param value what we're rounding
     * @param places how many places, we'll usually use 2
     * @return the rounded double
     * 
     * Found at: https://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
     * This is the example marked as the best answer under the heading "So, use this instead"
     */
    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
