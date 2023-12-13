package com.comix.model.comic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.comix.persistence.json.ComicJsonDeserializer;
import com.comix.persistence.json.ComicJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * An object that represents a comic that has been signed and maybe
 * authenticated.
 * This object stores a comic and wraps it with features related to signatures.
 * 
 * @author Alec Kovalczik
 */
@JsonDeserialize(using = ComicJsonDeserializer.class)
@JsonSerialize(using = ComicJsonSerializer.class)
public class SignatureDecorator<C extends Comic> extends ComicDecorator<C> {
    private C comic;
    private Map<String, Boolean> signatures = null; // HashMap<signature, authenticated>

    /**
     * Constructor for a SignatureDecorator
     * 
     * @param comic         Comic for the decorator to wrap
     * @param signature     Signature being assigned to the comic
     * @param authenticated Boolean representing whether or not the signature has
     *                      been authenticated
     * @throws Exception
     */
    public SignatureDecorator(C comic, String signature, Boolean authenticated) throws Exception {
        this.comic = comic;
        if (isSigned() == true) { // If the comic is already signed, add the new signature to the old
                                  // SignatureDecorator
            SignatureDecorator<C> previousSignatures = findSigned();
            previousSignatures.addSignature(signature, authenticated);
            throw new Exception("Signature added to the preexisting list"); // Just to break the constructor after
                                                                            // operating on the old decorator
        } else { // Otherwise create a SignatureDecorator.
            signatures = new HashMap<String, Boolean>();
            signatures.put(signature, authenticated);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Comic copy() {
        Map<String, Boolean> data = getSignatures();
        Comic comic = (C) getComic().copy();
        for (String signature : data.keySet()) {
            try {
                comic = new SignatureDecorator<>(comic, signature, signatures.get(signature));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return comic;
    }

    /**
     * Accessor for the value of the comic
     * 
     * @return the value of the comic being wrapped after beign altered by the
     *         signature.
     */
    @Override
    public double getValue() {
        double value = comic.getValue();
        for (Boolean authenticated : signatures.values()) {
            value = value * 1.05;
            if (authenticated == true) {
                value = value * 1.20;
            }
        }
        return round(value, 2);
    }

    /**
     * Accessor for the comic being wrapped by the SignatureDecorator
     * 
     * @return the comic this wraps
     */
    @Override
    public Comic getComic() {
        return comic;
    }

    /**
     * Accessor for the signatures of the comic
     * 
     * @return a map of signatures and their authentication status
     */
    public Map<String, Boolean> getSignatures() {
        return signatures;
    }

    /**
     * Mutator for the signature of the comic
     * 
     * @param signature     String representing a signature
     * @param authenticated Boolean representing if the signature has been
     *                      authenticated
     */
    public void addSignature(String signature, Boolean authenticated) {
        signatures.put(signature, authenticated);
    }

    /**
     * Mutator for the signature of the comic
     * 
     * @param signature String representing a signature
     */
    public void removeSignature(String signature) {
        signatures.remove(signature);
    }

    /**
     * Check if the comic this wraps is already signed
     * 
     * @return true if the comic has already been signed | false otherwise
     */
    @SuppressWarnings("unchecked")
    public boolean isSigned() {
        boolean result = false;
        C comicCopy = comic;
        while (comicCopy != null) {
            if (comicCopy instanceof SignatureDecorator<?>) {
                result = true;
            }
            comicCopy = (C) comicCopy.getComic();
        }
        return result;
    }

    /**
     * Finds and returns the SignatureDecorator in the decorator stack on comic
     * 
     * @return the SignatureDecorator in the decorator stack, so we can take action
     *         on it | null if not signatured
     */
    @SuppressWarnings("unchecked")
    public SignatureDecorator<C> findSigned() {
        SignatureDecorator<C> result = null;
        C comicCopy = comic;
        while (comicCopy != null) {
            if (comicCopy instanceof SignatureDecorator<?>) {
                result = (SignatureDecorator<C>) comicCopy;
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
        return comic.toString() + " *Signed: " + signatures.toString() + "*";
    }
}