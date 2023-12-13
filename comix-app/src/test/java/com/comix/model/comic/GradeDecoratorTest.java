package com.comix.model.comic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the GradeDecorator class
 * 
 * @author Alec Kovalczik
 */
@Tag("Model-Tier")
public class GradeDecoratorTest {
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
    private Comic ungradedComicDefault;
    private Comic ungradedComic;
    private GradeDecorator<Comic> gradedComicDefault;
    private GradeDecorator<Comic> gradedComic;

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

        ungradedComicDefault = new ConcreteComic(publisher, series, volume, issue, publicationDate, null, null, null, null, null);
        ungradedComic = new ConcreteComic(publisher, series, volume, issue, publicationDate, title, creators, principleCharacters, description, value);
        try {
            gradedComicDefault = new GradeDecorator<Comic>(new ConcreteComic(publisher, series, volume, issue, publicationDate, null, null, null, null, null), 1);
            gradedComic = new GradeDecorator<Comic>(new ConcreteComic(publisher, series, volume, issue, publicationDate, title, creators, principleCharacters, description, value),7);
        } catch (Exception e) {

        }

    }

    @Test
    public void testGradeConstructorNotOneToTen(){
        // Setup

        // Invoke
        assertThrows(Exception.class, () -> new GradeDecorator<Comic>(ungradedComicDefault, 0));
        assertThrows(Exception.class, () -> new GradeDecorator<Comic>(ungradedComic, 11));
    }

    @Test
    public void testGradeConstructorAlreadyGraded(){
        // Setup

        // Invoke
        assertThrows(Exception.class, () -> new GradeDecorator<Comic>((Comic)(new GradeDecorator<Comic>(ungradedComicDefault, 1)), 1));
        assertThrows(Exception.class, () -> new GradeDecorator<Comic>((Comic)(new GradeDecorator<Comic>(ungradedComic, 1)), 1));
    }

    @Test
    public void testGradeConstructorSuccess(){
        // Setup

        // Invoke
        try {
            Comic gradeDecoratorDefault = new GradeDecorator<Comic>(ungradedComicDefault, 1);
            Comic gradeDecorator = new GradeDecorator<Comic>(ungradedComic, 1);
            
                    // Analyze
            assertEquals(gradeDecoratorDefault.getComic(), ungradedComicDefault, "Graded comic is not the same comic");
            assertEquals(gradeDecorator.getComic(), ungradedComic, "Graded comic is not the same comic");
            assertEquals(1 ,((GradeDecorator<Comic>)gradeDecoratorDefault).getGrade(), "Grade was not defined properly");
            assertEquals(1 ,((GradeDecorator<Comic>)gradeDecorator).getGrade(), "Grade was not defined properly");
        } catch (Exception e) {
            
        }
    }

    @Test
    public void testGetComicID(){
        // Setup

        // Invoke
        String comic2ID = gradedComic.getComicID();
        String comic1ID = gradedComicDefault.getComicID();
        String comic1IDPlusOne = Integer.toString(Integer.parseInt(comic1ID) + 1);
        // Analyze
        assertNotEquals(comic1ID, comic2ID, "Comics have the same id, they are not unique");
        assertEquals(comic1IDPlusOne, comic2ID, "Each comic is not one ID more than the last");
    }    

    @Test
    public void testGetComic(){
        // Setup
        try {
            gradedComic = new GradeDecorator<Comic>(ungradedComic, 10);
        } catch (Exception e) {
            
        }

        // Invoke
        Comic comic = gradedComic.getComic();

        // Analyze
        assertEquals(ungradedComic, comic, "Comic in GradeDecorator doesn't match the comic passed to it");
    }

    @Test
    public void testGetGrade(){
        // Setup

        // Invoke
        gradedComic.getGrade();
        // Analyze

    }

    @Test
    public void testGetPublisher(){
        // Setup
        
        // Invoke
        String expected = publisher;
        String result = gradedComic.getPublisher();
        // Analyze
        assertEquals(expected, result, "Didn't get publisher properly"); 
    }

    @Test 
    public void testSetPublisher(){
        // Setup

        // Invoke
        String expected = "New";
        gradedComic.setPublisher(expected);
        String result = gradedComic.getPublisher();
        // Analyze
        assertEquals(expected, result, "setPublisher didn't change the publisher properly");
    }

    @Test
    public void testGetSeries(){
        // Setup
        
        // Invoke
        String expected = series;
        String result = gradedComic.getSeries();
        // Analyze
        assertEquals(expected, result, "Didn't get series properly"); 
    }

    @Test 
    public void testSetSeries(){
        // Setup

        // Invoke
        String expected = "New";
        gradedComic.setSeries(expected);
        String result = gradedComic.getSeries();
        // Analyze
        assertEquals(expected, result, "setSeries didn't change the series properly");
    }

    @Test
    public void testGetVolume(){
        // Setup
        
        // Invoke
        String expected = volume;
        String result = gradedComic.getVolume();
        // Analyze
        assertEquals(expected, result, "Didn't get volume properly"); 
    }

    @Test 
    public void testSetVolume(){
        // Setup

        // Invoke
        String expected = "New";
        gradedComic.setVolume(expected);
        String result = gradedComic.getVolume();
        // Analyze
        assertEquals(expected, result, "setVolume didn't change the volumeIssue");
    }

    @Test
    public void testGetIssue(){
        // Setup
        
        // Invoke
        String expected = issue;
        String result = gradedComic.getIssue();
        // Analyze
        assertEquals(expected, result, "Didn't get issue properly"); 
    }

    @Test 
    public void testSetIssue(){
        // Setup

        // Invoke
        String expected = "New";
        gradedComic.setIssue(expected);
        String result = gradedComic.getIssue();
        // Analyze
        assertEquals(expected, result, "setIssue didn't change the issue properly");
    }

    @Test
    public void testGetPublicationDate(){
        // Setup
        
        // Invoke
        String expected = publicationDate;
        String result = gradedComic.getPublicationDate();
        // Analyze
        assertEquals(expected, result, "Didn't get publicationDate properly"); 
    }

    @Test 
    public void testSetPublicationDate(){
        // Setup

        // Invoke
        String expected = "2023-10-22";
        gradedComic.setPublicationDate(expected);
        String result = gradedComic.getPublicationDate();
        // Analyze
        assertEquals(expected, result, "setPublicationDate didn't change the publicationDate properly");
    }

    @Test
    public void testGetComicTitle(){
        // Setup

        // Invoke
        String result1 = gradedComicDefault.getComicTitle();
        String result2 = gradedComic.getComicTitle();
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
        gradedComicDefault.setComicTitle(expected1);
        gradedComic.setComicTitle(expected2);
        String result1 = gradedComicDefault.getComicTitle();
        String result2 = gradedComic.getComicTitle();
        // Analyze
        assertEquals(expected1, result1, "The new title didn't replace the null title");
        assertEquals(expected2, result2, "The new title didn't replace the old title");
    }

    @Test
    public void testGetCreators(){
        // Setup

        // Invoke
        ArrayList<String> result1 = gradedComicDefault.getCreators();
        ArrayList<String> result2 = gradedComic.getCreators();
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
        gradedComicDefault.setCreators(expected);
        gradedComic.setCreators(expected);
        ArrayList<String> result1 = gradedComicDefault.getCreators();
        ArrayList<String> result2 = gradedComic.getCreators();
        // Analyze
        assertEquals(expected, result1, "The new creators didn't replace the null creators");
        assertEquals(expected, result2, "The new creators didn't replace the old creators");
    }
    
    @Test
    public void testGetPrincipleCharacters(){
        // Setup

        // Invoke
        ArrayList<String> result1 = gradedComicDefault.getPrincipleCharacters();
        ArrayList<String> result2 = gradedComic.getPrincipleCharacters();
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
        gradedComicDefault.setPrincipleCharacters(expected);
        gradedComic.setPrincipleCharacters(expected);
        ArrayList<String> result1 = gradedComicDefault.getPrincipleCharacters();
        ArrayList<String> result2 = gradedComic.getPrincipleCharacters();
        // Analyze
        assertEquals(expected, result1, "The new principleCharacters didn't replace the null principleCharacters");
        assertEquals(expected, result2, "The new principleCharacters didn't replace the old principleCharacters");
    }

    @Test
    public void testGetDescription(){
        // Setup

        // Invoke
        String result1 = gradedComicDefault.getDescription();
        String result2 = gradedComic.getDescription();
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
        gradedComicDefault.setDescription(expected1);
        gradedComic.setDescription(expected2);
        String result1 = gradedComicDefault.getDescription();
        String result2 = gradedComic.getDescription();
        // Analyze
        assertEquals(expected1, result1, "The new title didn't replace the null title");
        assertEquals(expected2, result2, "The new title didn't replace the old title");
    }

    @Test
    public void testGetValue(){
        // Setup

        // Invoke
        double result1 = gradedComicDefault.getValue();
        double result2 = gradedComic.getValue();
        double expected1 = round(0, 2);
        double expected2 = round(value * Math.log10(7), 2);
        // Analyze
        assertEquals(expected1, result1, "getValue is not returning 0 by default");
        assertEquals(expected2, result2, "getValue is not returning the value when specified");
    }

    @Test
    public void testSetValue(){
        // Setup

        // Invoke
        double comicValue1 = 10.0;
        double comicValue2 = 15.5;
        gradedComicDefault.setValue(comicValue1);
        gradedComic.setValue(comicValue2);
        double expected1 = round(comicValue1 * .1, 2);
        double expected2 = round(comicValue2 * Math.log10(7), 2);
        double result1 = gradedComicDefault.getValue();
        double result2 = gradedComic.getValue();
        // Analyze
        assertEquals(expected1, result1, "The new value didn't replace the null value");
        assertEquals(expected2, result2, "The new value didn't replace the old value");
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
