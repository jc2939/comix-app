package com.comix.model.comic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.comix.model.comic.comicComparators.DefaultComparator;
import com.comix.model.comic.comicComparators.PublicationDateComparator;

/**
 * The unit test suite for the SlabDecorator class
 * 
 * @author Alec Kovalczik
 */
@Tag("Model-Tier")
public class SlabDecoratorTest {
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
    private SlabDecorator<Comic> slabbedComicDefault;
    private SlabDecorator<Comic> slabbedComic;

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

            slabbedComicDefault = new SlabDecorator<>(gradedComicDefault, true);
            slabbedComic = new SlabDecorator<>(gradedComic, true);
        } catch (Exception e) {

        }

    }

    @Test
    public void testSlabDecoratorConstructorSuccess(){
        // Setup

        // Invoke
        try {
            Comic slabDecoratorDefault = new SlabDecorator<Comic>(gradedComicDefault, true);
            Comic slabDecorator = new SlabDecorator<Comic>(gradedComic, true);
            
            // Analyze
            assertEquals(gradedComicDefault, slabDecoratorDefault.getComic(), "slabDecoratorDefault didn't wrap the comic correctly");
            assertEquals(gradedComic, slabDecorator.getComic(), "slabDecorator didn't wrap the comic correctly");
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testSlabDecoratorConstructorNotGraded(){
        // Setup

        // Invoke
        try {
            // Analyze
            assertThrows(Exception.class, () -> new SlabDecorator<>(ungradedComicDefault, true));
            assertThrows(Exception.class, () -> new SlabDecorator<>(ungradedComic, true));
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testSlabDecoratorConstructorAlreadySlabbed(){
        // Setup

        // Invoke
        try {
            // Analyze
            assertThrows(Exception.class, () -> new SlabDecorator<>((Comic)(new SlabDecorator<>(gradedComicDefault, true)), true));
            assertThrows(Exception.class, () -> new SlabDecorator<>((Comic)(new SlabDecorator<>(gradedComic, true)), true));
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test 
    public void testComparitors(){
        // Setup
        Comic ungraded1 = new ConcreteComic("a", "b", "b", "b", "Feb 02, 2023", null, null, null, null, null);
        Comic ungraded2 = new ConcreteComic("b", "c", "c", "c", "Jan 02, 2023", title, creators, null, description, null);
        Comic ungraded3 = new ConcreteComic("c", "d", "d", "a", "Jan 01, 2023", null, creators, principleCharacters, description, null);
        Comic graded1;
        Comic graded2; 
        Comic graded3; 
        Comic slabbed1;
        Comic slabbed2; 
        Comic slabbed3; 
        try {
            graded1 = new GradeDecorator<Comic>((Comic)(new ConcreteComic("a", "b", "b", "a", "Mar 22, 2020", null, null, null, null, null)), 5);
            graded2 = new GradeDecorator<Comic>((Comic)(new ConcreteComic("b", "a", "b", "c", "Mar 22, 2022", title, creators, null, description, null)), 6);
            graded3 = new GradeDecorator<Comic>((Comic)(new ConcreteComic("c", "d", "d", "a", "Mar 21, 2022", null, creators, principleCharacters, description, null)), 10);

            slabbed1 = new SlabDecorator<Comic>((Comic)(new GradeDecorator<Comic>((Comic)(new ConcreteComic("a", "b", "c", "b", "Oct 12, 2023", null, null, null, null, null)), 5)), true);
            slabbed2 = new SlabDecorator<Comic>((Comic)(new GradeDecorator<Comic>((Comic)(new ConcreteComic("b", "c", "b", "a", "Sep 10, 2023", title, creators, null, description, null)), 6)), true);
            slabbed3 = new SlabDecorator<Comic>((Comic)(new GradeDecorator<Comic>((Comic)(new ConcreteComic("c", "a", "b", "a", "Feb 01, 2023", null, creators, principleCharacters, description, null)), 10)), true);

            ArrayList<Comic> comics = new ArrayList<>(){{
                add(ungraded1);
                add(ungraded2);
                add(ungraded3);
                add(graded1);
                add(graded2);
                add(graded3);
                add(slabbed1);
                add(slabbed2);
                add(slabbed3);
            }};

            ArrayList<Comic> comicsSortedDefault = new ArrayList<>(){{
                // a
                add(slabbed3);
                add(graded2);
                
                // b
                add(graded1);
                add(ungraded1);
                add(slabbed1);

                // c
                add(slabbed2);
                add(ungraded2);

                // d
                add(ungraded3);
                add(graded3);
                
            }};

            ArrayList<Comic> comicsSortedPublicationDate = new ArrayList<>(){{
                // 2020
                add(graded1);

                // 2022
                add(graded3);
                add(graded2);

                // 2023
                    // Jan
                    add(ungraded3);
                    add(ungraded2);

                    // Feb
                    add(slabbed3);
                    add(ungraded1);

                    // Sep
                    add(slabbed2);

                    // Oct
                    add(slabbed1);

                
            }};

            Comparator<Comic> defaultComparator = new DefaultComparator();
            Comparator<Comic> publicationDateComparator = new PublicationDateComparator();
            // Invoke
            
            // Analyze
            comics.sort(defaultComparator);
            assertEquals(comicsSortedDefault, comics, "Didn't sort by default properly");

            comics.sort(publicationDateComparator);
            // assertEquals(comicsSortedPublicationDate, comics, "Didn't sort by publicationDate properly");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetComicID(){
        // Setup

        // Invoke

        String comic2ID = slabbedComic.getComicID();
        String comic1ID = slabbedComicDefault.getComicID();
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
        String result = slabbedComic.getPublisher();
        // Analyze
        assertEquals(expected, result, "Didn't get publisher properly"); 
    }

    @Test 
    public void testSetPublisher(){
        // Setup

        // Invoke
        String expected = "New";
        slabbedComic.setPublisher(expected);
        String result = slabbedComic.getPublisher();
        // Analyze
        assertEquals(expected, result, "setPublisher didn't change the publisher properly");
    }

    @Test
    public void testGetSeries(){
        // Setup
        
        // Invoke
        String expected = series;
        String result = slabbedComic.getSeries();
        // Analyze
        assertEquals(expected, result, "Didn't get series properly"); 
    }

    @Test 
    public void testSetSeries(){
        // Setup

        // Invoke
        String expected = "New";
        slabbedComic.setSeries(expected);
        String result = slabbedComic.getSeries();
        // Analyze
        assertEquals(expected, result, "setSeries didn't change the series properly");
    }

    @Test
    public void testGetVolume(){
        // Setup
        
        // Invoke
        String expected = volume;
        String result = slabbedComic.getVolume();
        // Analyze
        assertEquals(expected, result, "Didn't get volume properly"); 
    }

    @Test 
    public void testSetVolume(){
        // Setup

        // Invoke
        String expected = "New";
        slabbedComic.setVolume(expected);
        String result = slabbedComic.getVolume();
        // Analyze
        assertEquals(expected, result, "setVolume didn't change the volumeIssue");
    }

    @Test
    public void testGetIssue(){
        // Setup
        
        // Invoke
        String expected = issue;
        String result = slabbedComic.getIssue();
        // Analyze
        assertEquals(expected, result, "Didn't get issue properly"); 
    }

    @Test 
    public void testSetIssue(){
        // Setup

        // Invoke
        String expected = "New";
        slabbedComic.setIssue(expected);
        String result = slabbedComic.getIssue();
        // Analyze
        assertEquals(expected, result, "setIssue didn't change the issue properly");
    }

    @Test
    public void testGetPublicationDate(){
        // Setup
        
        // Invoke
        String expected = publicationDate;
        String result = slabbedComic.getPublicationDate();
        // Analyze
        assertEquals(expected, result, "Didn't get publicationDate properly"); 
    }

    @Test 
    public void testSetPublicationDate(){
        // Setup

        // Invoke
        String expected = "2023-10-22";
        slabbedComic.setPublicationDate(expected);
        String result = slabbedComic.getPublicationDate();
        // Analyze
        assertEquals(expected, result, "setPublicationDate didn't change the publicationDate properly");
    }

    @Test
    public void testGetComicTitle(){
        // Setup

        // Invoke
        String result1 = slabbedComicDefault.getComicTitle();
        String result2 = slabbedComic.getComicTitle();
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
        slabbedComicDefault.setComicTitle(expected1);
        slabbedComic.setComicTitle(expected2);
        String result1 = slabbedComicDefault.getComicTitle();
        String result2 = slabbedComic.getComicTitle();
        // Analyze
        assertEquals(expected1, result1, "The new title didn't replace the null title");
        assertEquals(expected2, result2, "The new title didn't replace the old title");
    }

    @Test
    public void testGetCreators(){
        // Setup

        // Invoke
        ArrayList<String> result1 = slabbedComicDefault.getCreators();
        ArrayList<String> result2 = slabbedComic.getCreators();
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
        slabbedComicDefault.setCreators(expected);
        slabbedComic.setCreators(expected);
        ArrayList<String> result1 = slabbedComicDefault.getCreators();
        ArrayList<String> result2 = slabbedComic.getCreators();
        // Analyze
        assertEquals(expected, result1, "The new creators didn't replace the null creators");
        assertEquals(expected, result2, "The new creators didn't replace the old creators");
    }
    
    @Test
    public void testGetPrincipleCharacters(){
        // Setup

        // Invoke
        ArrayList<String> result1 = slabbedComicDefault.getPrincipleCharacters();
        ArrayList<String> result2 = slabbedComic.getPrincipleCharacters();
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
        slabbedComicDefault.setPrincipleCharacters(expected);
        slabbedComic.setPrincipleCharacters(expected);
        ArrayList<String> result1 = slabbedComicDefault.getPrincipleCharacters();
        ArrayList<String> result2 = slabbedComic.getPrincipleCharacters();
        // Analyze
        assertEquals(expected, result1, "The new principleCharacters didn't replace the null principleCharacters");
        assertEquals(expected, result2, "The new principleCharacters didn't replace the old principleCharacters");
    }

    @Test
    public void testGetDescription(){
        // Setup

        // Invoke
        String result1 = slabbedComicDefault.getDescription();
        String result2 = slabbedComic.getDescription();
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
        slabbedComicDefault.setDescription(expected1);
        slabbedComic.setDescription(expected2);
        String result1 = slabbedComicDefault.getDescription();
        String result2 = slabbedComic.getDescription();
        // Analyze
        assertEquals(expected1, result1, "The new title didn't replace the null title");
        assertEquals(expected2, result2, "The new title didn't replace the old title");
    }

    @Test
    public void testGetValue(){
        // Setup

        // Invoke
        double result1 = slabbedComicDefault.getValue();
        double result2 = slabbedComic.getValue();
        double expected1 = round(0, 2);
        double expected2 = 42.26;
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
        slabbedComicDefault.setValue(comicValue1);
        slabbedComic.setValue(comicValue2);
        double expected1 = round(comicValue1 * .1 * 2, 2);
        double expected2 = round(comicValue2 * Math.log10(7) * 2, 2);
        double result1 = slabbedComicDefault.getValue();
        double result2 = slabbedComic.getValue();
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
