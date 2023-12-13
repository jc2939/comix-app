package com.comix.model.comic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import com.comix.persistence.json.ComicJsonDeserializer;
import com.comix.persistence.json.ComicJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * An object that represents a comic that has been slabbed.
 * This object stores a comic and wraps it with features related to slabbing a
 * comic.
 * For SlabDecorator the comic stored within it has to have a GradeDecorator
 * somewhere
 * on it and it can't already be slabbed.
 * 
 * @author Alec Kovalczik
 */
@JsonDeserialize(using = ComicJsonDeserializer.class)
@JsonSerialize(using = ComicJsonSerializer.class)
public class SlabDecorator<C extends Comic> extends ComicDecorator<C> {
    private C comic;
    private boolean slabbed;

    /**
     * Creates a SlabDecorator using a comic to wrap. If the comic passed to it is
     * not graded, or if the comic is already slabbed, then throw an exception
     * indicating what is wrong and cancelling the slab decorator's creation.
     * 
     * @param comic comic being wrapped by the decorator
     * @throws Exception if the comic is not graded | if the comic is already
     *                   slabbed
     */
    public SlabDecorator(C comic, boolean isSlabbed) throws Exception {
        this.comic = comic;
        if (isGraded() == false) {
            throw new Exception("Comic must be graded before it can be slabbed");
        }
        if (isSlabbed() == true) {
            SlabDecorator<C> slabDecorator = findSlabbed();
            slabDecorator.setSlabbed(isSlabbed);
            throw new Exception("Comic is already slabbed");
        }
        this.slabbed = isSlabbed;

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
    private SlabDecorator(C comic, int unused) {
        this.comic = (C) comic.copy();
        this.slabbed = true;

    }

    @Override
    public Comic copy() {
        return new SlabDecorator<>(getComic(), 0);
    }

    /**
     * Accessor for the value of the comic
     * 
     * @return the value of the comic being wrapped after beign altered by slabbed
     */
    @Override
    public double getValue() {
        double value = comic.getValue();
        if (slabbed) {
            value = value * 2;
        }
        return round(value, 2);
    }

    /**
     * Accessor for the comic being wrapped by the SlabDecorator
     * 
     * @return the comic this wraps
     */
    @Override
    public C getComic() {
        return comic;
    }

    /**
     * Accessor for slabbed
     * 
     * @return true if the comic is slabbed, and false otherwise
     */
    public boolean getSlabbed() {
        return slabbed;
    }

    /**
     * Mutator for slabbed
     * 
     * @param slabbed new slabbed value, used if you need to change whether the book
     *                is slabbed or not
     */
    public void setSlabbed(boolean slabbed) {
        this.slabbed = slabbed;
    }

    /**
     * Checks if the comic being wrapped is already graded
     * 
     * @return true if the comic is graded | false otherwise
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
     * Checks if the comic being wrapped is already slabbed
     * 
     * @return true if the comic is already slabbed | false otherwise
     */
    @SuppressWarnings("unchecked")
    public boolean isSlabbed() {
        boolean result = false;
        C comicCopy = comic;
        while (comicCopy != null) {
            if (comicCopy instanceof SlabDecorator<?>) {
                result = true;
            }
            comicCopy = (C) comicCopy.getComic();
        }
        return result;
    }

    /**
     * Finds and returns the SlabDecorator in the decorator stack on comic
     * 
     * @return the SlabDecorator in the decorator stack, so we can take action on it
     *         | null if not graded
     */
    @SuppressWarnings("unchecked")
    public SlabDecorator<C> findSlabbed() {
        SlabDecorator<C> result = null;
        C comicCopy = comic;
        while (comicCopy != null) {
            if (comicCopy instanceof SlabDecorator<?>) {
                result = (SlabDecorator<C>) comicCopy;
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
        return comic.toString() + " *Slabbed*";
    }
}