package com.comix.model.comic;

import java.util.ArrayList;

import com.comix.persistence.json.ComicJsonDeserializer;
import com.comix.persistence.json.ComicJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Abstract class used to provide all of the decorators with a reference to a
 * Comic through a generic,
 * aswell as basic implementations for some methods all decorators need to do
 * their job and
 * a basic implementation of some accessors and mutators for ConcreteComic
 * fields.
 * 
 * @author Alec Kovalczik
 */
@JsonDeserialize(using = ComicJsonDeserializer.class)
@JsonSerialize(using = ComicJsonSerializer.class)
abstract class ComicDecorator<C extends Comic> implements Comic {
    private C comic; // Comic being wrapped by the decorator

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
    public double getValue() {
        return comic.getValue();
    }

    @Override
    public void setValue(double value) {
        comic.setValue(value);
    }

    @Override
    public Comic getComic() {
        return comic;
    }
}