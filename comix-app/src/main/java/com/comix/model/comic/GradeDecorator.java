package com.comix.model.comic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import com.comix.persistence.json.ComicJsonDeserializer;
import com.comix.persistence.json.ComicJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * An object that represents a comic that has been graded.
 * This object stores a comic and wraps it with features related to grade.
 * For a grade decorator, the comic it stores can't already be graded.
 * 
 * @author Alec Kovalczik
 */
@JsonDeserialize(using = ComicJsonDeserializer.class)
@JsonSerialize(using = ComicJsonSerializer.class)
public class GradeDecorator<C extends Comic> extends ComicDecorator<C> {
    private C comic;
    private int grade;

    /**
     * Constructor for a GradeDecorator
     * 
     * @param comic Comic for the decorator to wrap
     * @param grade Grade being assigned to the comic (should be an int 1-10)
     * @throws Exception if the grade is not within int 1-10 or the comic being
     *                   passed in is already graded
     */
    public GradeDecorator(C comic, int grade) throws Exception {
        this.comic = comic;
        if (grade >= 1 && grade <= 10) { // If the grade is not within int 1-10, throw an exception
            this.grade = grade;
        } else {
            throw new Exception("Grade can only be 1 - 10");
        }
        if (isGraded() == true) { // If the comic is already graded, regrade it in the grade Decorator that
                                  // already exists
            GradeDecorator<C> previousGrade = findGraded();
            previousGrade.setGrade(grade);
            // throw exception to cancel the constructor
            throw new Exception("Comic already graded, changing grade instead of adding a new decorator");
        }
    }

    /**
     * Constructor for a GradeDecorator
     * 
     * @param comic Comic for the decorator to wrap
     * @param grade Grade being assigned to the comic (should be an int 1-10)
     * @throws Exception if the grade is not within int 1-10 or the comic being
     *                   passed in is already graded
     */
    @SuppressWarnings("unchecked")
    private GradeDecorator(C comic, int grade, int unused) {
        this.comic = (C) comic.copy();
        this.grade = grade;

    }

    @Override
    public Comic copy() {
        return new GradeDecorator<>(getComic(), getGrade(), 0);
    }

    /**
     * Accessor for the value of the comic
     * 
     * @return the value of the comic being wrapped after beign altered by the
     *         grade.
     */
    @Override
    public double getValue() {
        double value = comic.getValue();
        if (grade == 1) {
            value = value * 0.10;
        } else {
            value = value * Math.log10(grade);
        }
        return round(value, 2);
    }

    /**
     * Accessor for the comic being wrapped by the GradeDecorator
     * 
     * @return the comic this wraps
     */
    @Override
    public Comic getComic() {
        return comic;
    }

    /**
     * Accessor for the grade of the comic
     * 
     * @return grade (int 1-10)
     */
    public int getGrade() {
        return grade;
    }

    /**
     * Mutator for the grade of the comic
     * 
     * @param grade int (1-10)
     * @throws Exception if the grade is not in int 1-10 throw an exception
     */
    public void setGrade(int grade) throws Exception {
        if (grade >= 1 && grade <= 10) { // If the grade is not within int 1-10, throw an exception
            this.grade = grade;
        } else {
            throw new Exception("Grade can only be 1 - 10");
        }
    }

    /**
     * Check if the comic this wraps is already graded
     * 
     * @return true if the comic has already been graded | false otherwise
     */
    @SuppressWarnings("unchecked")
    public boolean isGraded() {
        boolean result = false;
        C comicCopy = comic;
        while (comicCopy != null) {
            if (comicCopy instanceof GradeDecorator<?>) {
                result = true;
            }
            comicCopy = (C) comicCopy.getComic();
        }
        return result;
    }

    /**
     * Finds and returns the GradeDecorator in the decorator stack on comic
     * 
     * @return the GradeDecorator in the decorator stack, so we can take action on
     *         it | null if not graded
     */
    @SuppressWarnings("unchecked")
    public GradeDecorator<C> findGraded() {
        GradeDecorator<C> result = null;
        C comicCopy = comic;
        while (comicCopy != null) {
            if (comicCopy instanceof GradeDecorator<?>) {
                result = (GradeDecorator<C>) comicCopy;
            }
            comicCopy = (C) comicCopy.getComic();
        }
        return result;
    }

    @Override
    public String getComicID() {
        return comic.getComicID();
    }

    @Override
    public String getComicTitle() {
        return comic.getComicTitle();
    }

    @Override
    public void setComicTitle(String comicTitle) {
        comic.setComicTitle(comicTitle);
    }

    @Override
    public String getPublisher() {
        return comic.getPublisher();
    }

    @Override
    public void setPublisher(String publisher) {
        comic.setPublisher(publisher);
    }

    @Override
    public String getSeries() {
        return comic.getSeries();
    }

    @Override
    public void setSeries(String series) {
        comic.setSeries(series);
    }

    @Override
    public String getVolume() {
        return comic.getVolume();
    }

    @Override
    public void setVolume(String volume) {
        comic.setVolume(volume);
    }

    @Override
    public String getIssue() {
        return comic.getIssue();
    }

    @Override
    public void setIssue(String issue) {
        comic.setIssue(issue);
    }

    @Override
    public String getPublicationDate() {
        return comic.getPublicationDate();
    }

    @Override
    public void setPublicationDate(String publicationDate) {
        comic.setPublicationDate(publicationDate);
        ;
    }

    @Override
    public ArrayList<String> getCreators() {
        return comic.getCreators();
    }

    @Override
    public void setCreators(ArrayList<String> creators) {
        comic.setCreators(creators);
    }

    @Override
    public ArrayList<String> getPrincipleCharacters() {
        return comic.getPrincipleCharacters();
    }

    @Override
    public void setPrincipleCharacters(ArrayList<String> principleCharacters) {
        comic.setPrincipleCharacters(principleCharacters);
    }

    @Override
    public String getDescription() {
        return comic.getDescription();
    }

    @Override
    public void setDescription(String description) {
        comic.setDescription(description);
    }

    @Override
    public void setValue(double value) {
        comic.setValue(value);
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

    @Override
    public String toString() {
        return comic.toString() + " *Graded: " + getGrade() + "*";
    }
}