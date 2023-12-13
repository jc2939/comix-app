package com.comix.model.comic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the SignatureDecorator class
 * 
 * @author Alec Kovalczik
 */
@Tag("Model-Tier")
public class SignatureDecoratorTest {
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
    private Comic unsignedComicDefault;
    private Comic unsignedComic;
    private SignatureDecorator<Comic> signedComicDefault;
    private SignatureDecorator<Comic> signedComic;


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
        publicationDate = "2003-09-16";
        description = "Bobby is the main character :)";
        value = 25.00;

        unsignedComicDefault = new ConcreteComic(publisher, series, volume, issue, publicationDate, null, null, null, null, null);
        unsignedComic = new ConcreteComic(publisher, series, volume, issue, publicationDate, title, creators, principleCharacters, description, value);
        try {
            signedComicDefault = new SignatureDecorator<Comic>(new ConcreteComic(publisher, series, volume, issue, publicationDate, null, null, null, null, null), "Alec Kovalczik", false);
            signedComic = new SignatureDecorator<Comic>(new ConcreteComic(publisher, series, volume, issue, publicationDate, title, creators, principleCharacters, description, value), "Alec Kovalczik", true);
        } catch (Exception e) {

        }

        try {
            signedComic = new SignatureDecorator<Comic>(signedComic, "Rylie Love", false);
        } catch (Exception e) {

        }

        try {
            signedComic = new SignatureDecorator<Comic>(signedComic, "Laurie Walter", false);
        } catch (Exception e) {

        }

    }

    @Test
    public void testSignatureConstructorAlreadySigned(){
        // Setup

        // Invoke
        assertThrows(Exception.class, () -> new SignatureDecorator<Comic>((Comic)(new SignatureDecorator<Comic>(unsignedComicDefault, "Alec Kovalczik", false)), "Alec Kovalczik", false));
        assertThrows(Exception.class, () -> new SignatureDecorator<Comic>((Comic)(new SignatureDecorator<Comic>(unsignedComic, "Alec Kovalczik", false)), "Alec Kovalczik", false));
    }

    @Test
    public void testSignatureConstructorSuccess(){
        // Setup
        Map<String, Boolean> expected = new HashMap<>();
        expected.put("Alec Kovalczik", false);

        // Invoke
        try {
            Comic SignatureDecoratorDefault = new SignatureDecorator<Comic>(unsignedComicDefault, "Alec Kovalczik", false);
            Comic SignatureDecorator = new SignatureDecorator<Comic>(unsignedComic, "Alec Kovalczik", false);
                    // Analyze
            assertEquals(SignatureDecoratorDefault.getComic(), unsignedComicDefault, "Signed comic is not the same comic");
            assertEquals(SignatureDecorator.getComic(), unsignedComic, "Signed comic is not the same comic");
            assertEquals(expected ,((SignatureDecorator<Comic>)SignatureDecoratorDefault).getSignatures(), "Signature was not defined properly");
            assertEquals(expected ,((SignatureDecorator<Comic>)SignatureDecorator).getSignatures(), "Signature was not defined properly");
        } catch (Exception e) {
            
        }
    }

    @Test
    public void testGetComicID(){
        // Setup

        // Invoke
        String comic2ID = signedComic.getComicID();
        String comic1ID = signedComicDefault.getComicID();
        String comic1IDPlusOne = Integer.toString(Integer.parseInt(comic1ID) + 1);
        // Analyze
        assertNotEquals(comic1ID, comic2ID, "Comics have the same id, they are not unique");
        assertEquals(comic1IDPlusOne, comic2ID, "Each comic is not one ID more than the last");
    }    

    @Test
    public void testGetComic(){
        // Setup
        try {
            signedComic = new SignatureDecorator<Comic>(unsignedComic, "Alec Kovalczik", false);
        } catch (Exception e) {
            
        }

        // Invoke
        Comic comic = signedComic.getComic();

        // Analyze
        assertEquals(unsignedComic, comic, "Comic in SignatureDecorator doesn't match the comic passed to it");
    }

    @Test
    public void testGetSignatures(){
        // Setup

        // Invoke
        signedComic.getSignatures();
        // Analyze

    }

    @Test
    public void testGetPublisher(){
        // Setup
        
        // Invoke
        String expected = publisher;
        String result = signedComic.getPublisher();
        // Analyze
        assertEquals(expected, result, "Didn't get publisher properly"); 
    }

    @Test 
    public void testSetPublisher(){
        // Setup

        // Invoke
        String expected = "New";
        signedComic.setPublisher(expected);
        String result = signedComic.getPublisher();
        // Analyze
        assertEquals(expected, result, "setPublisher didn't change the publisher properly");
    }

    @Test
    public void testGetSeries(){
        // Setup
        
        // Invoke
        String expected = series;
        String result = signedComic.getSeries();
        // Analyze
        assertEquals(expected, result, "Didn't get series properly"); 
    }

    @Test 
    public void testSetSeries(){
        // Setup

        // Invoke
        String expected = "New";
        signedComic.setSeries(expected);
        String result = signedComic.getSeries();
        // Analyze
        assertEquals(expected, result, "setSeries didn't change the series properly");
    }

    @Test
    public void testGetVolume(){
        // Setup
        
        // Invoke
        String expected = volume;
        String result = signedComic.getVolume();
        // Analyze
        assertEquals(expected, result, "Didn't get volume properly"); 
    }

    @Test 
    public void testSetVolume(){
        // Setup

        // Invoke
        String expected = "New";
        signedComic.setVolume(expected);
        String result = signedComic.getVolume();
        // Analyze
        assertEquals(expected, result, "setVolume didn't change the volumeIssue");
    }

    @Test
    public void testGetIssue(){
        // Setup
        
        // Invoke
        String expected = issue;
        String result = signedComic.getIssue();
        // Analyze
        assertEquals(expected, result, "Didn't get issue properly"); 
    }

    @Test 
    public void testSetIssue(){
        // Setup

        // Invoke
        String expected = "New";
        signedComic.setIssue(expected);
        String result = signedComic.getIssue();
        // Analyze
        assertEquals(expected, result, "setIssue didn't change the issue properly");
    }

    @Test
    public void testGetPublicationDate(){
        // Setup
        
        // Invoke
        String expected = publicationDate;
        String result = signedComic.getPublicationDate();
        // Analyze
        assertEquals(expected, result, "Didn't get publicationDate properly"); 
    }

    @Test 
    public void testSetPublicationDate(){
        // Setup

        // Invoke
        String expected = "2023-10-22";
        signedComic.setPublicationDate(expected);
        String result = signedComic.getPublicationDate();
        // Analyze
        assertEquals(expected, result, "setPublicationDate didn't change the publicationDate properly");
    }

    @Test
    public void testGetComicTitle(){
        // Setup

        // Invoke
        String result1 = signedComicDefault.getComicTitle();
        String result2 = signedComic.getComicTitle();
        String expected1 = "untitled";
        String expected2 = title;
        // Analyze
        assertEquals(expected1, result1, "getTitle is not returning untitled by default");
        assertEquals(expected2, result2, "getTitle is not returning the title when specified");
    }

    @Test
    public void testSetComicTitle(){
        // Setup

        // Invoke
        String expected1 = "Title";
        String expected2 = "New Title";
        signedComicDefault.setComicTitle(expected1);
        signedComic.setComicTitle(expected2);
        String result1 = signedComicDefault.getComicTitle();
        String result2 = signedComic.getComicTitle();
        // Analyze
        assertEquals(expected1, result1, "The new title didn't replace the null title");
        assertEquals(expected2, result2, "The new title didn't replace the old title");
    }

    @Test
    public void testGetCreators(){
        // Setup

        // Invoke
        ArrayList<String> result1 = signedComicDefault.getCreators();
        ArrayList<String> result2 = signedComic.getCreators();
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
        signedComicDefault.setCreators(expected);
        signedComic.setCreators(expected);
        ArrayList<String> result1 = signedComicDefault.getCreators();
        ArrayList<String> result2 = signedComic.getCreators();
        // Analyze
        assertEquals(expected, result1, "The new creators didn't replace the null creators");
        assertEquals(expected, result2, "The new creators didn't replace the old creators");
    }
    
    @Test
    public void testGetPrincipleCharacters(){
        // Setup

        // Invoke
        ArrayList<String> result1 = signedComicDefault.getPrincipleCharacters();
        ArrayList<String> result2 = signedComic.getPrincipleCharacters();
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
        signedComicDefault.setPrincipleCharacters(expected);
        signedComic.setPrincipleCharacters(expected);
        ArrayList<String> result1 = signedComicDefault.getPrincipleCharacters();
        ArrayList<String> result2 = signedComic.getPrincipleCharacters();
        // Analyze
        assertEquals(expected, result1, "The new principleCharacters didn't replace the null principleCharacters");
        assertEquals(expected, result2, "The new principleCharacters didn't replace the old principleCharacters");
    }

    @Test
    public void testGetDescription(){
        // Setup

        // Invoke
        String result1 = signedComicDefault.getDescription();
        String result2 = signedComic.getDescription();
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
        signedComicDefault.setDescription(expected1);
        signedComic.setDescription(expected2);
        String result1 = signedComicDefault.getDescription();
        String result2 = signedComic.getDescription();
        // Analyze
        assertEquals(expected1, result1, "The new title didn't replace the null title");
        assertEquals(expected2, result2, "The new title didn't replace the old title");
    }

    @Test
    public void testGetValue(){
        // Setup

        // Invoke
        double result1 = signedComicDefault.getValue();
        double result2 = signedComic.getValue();
        double expected1 = round(0, 2);
        double expected2 = round(round(round(round(value, 2) * 1.05 * 1.20, 2) * 1.05, 2) * 1.05, 2);
        // Analyze
        assertEquals(expected1, result1, "getValue is not returning 0 by default");
        assertEquals(expected2, result2, "getValue is not returning the value when specified");
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
