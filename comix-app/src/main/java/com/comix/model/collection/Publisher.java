package com.comix.model.collection;

import java.util.ArrayList;

import com.comix.model.collection.visitor.CollectionVisitor;
import com.comix.model.collection.visitor.Visitable;
import com.comix.model.comic.Comic;

import java.util.TreeMap;

/** Class to represent the Publisher composite tier of the project.
 * Publisher is a composite and holds a treemap of series, the next lower composite
*/
public class Publisher implements Collection, Visitable {
    private TreeMap<String, Series> series;
    private String name;

    /**
     * Main Constructor for Publisher 
     * @param series TreeMap<String, Series> representing the collection of series under a publisher
     * @param name String representing name of Publisher
     */
    public Publisher(TreeMap<String, Series> series, String name) {
        this.series = series;
        this.name = name;
    }
    
    /**
     * Alternate Constructor for Publisher
     * Instantiates an empty TreeMap of series as a basline
     * @param name Name of the publisher
     */
    public Publisher(String name) {
        this.name = name;
        this.series = new TreeMap<String, Series>();
    }
    
    /**
     * getValue() function to get the value of all the comics under a certain composite
     * Publisher calls getValue() in every series in its TreeMap
     * @return i (double)
     * 
     */
    public double getValue() {
        int i = 0;
        for (Series item : series.values()) {
            i += item.getValue();
        }
        return i;
    }
    
    /**
     * Remove method to remove entries from map
     * @param serie Series representing a series that you want to be removed from the TreeMap
     */
    public void remove(Collection serie) {
        series.remove(serie.getName());
    }
    
    /**
     * add method to add entries to the map
     * @param serie Series representing a series that you want to be add to the TreeMap
     */
    public void add(Collection serie) {
        series.put(serie.getName(), ((Series) serie));
    }
    
    /**
     *AddComic method to add a comic to a collection. 
     *If publisher,series,Volume,Issue field don't exist in the system then it will create them and add the comic appropriately
     *@param comic comic to be added into the system Contains all the necessarry information to place itself
     *@return comic (Comic)
     */
    public Comic addComic(Comic comic) {
        if (!series.containsKey(comic.getSeries())) {
            series.put(comic.getSeries(), new Series(comic.getSeries()));
        }
        series.get(comic.getSeries()).addComic(comic);
        return comic;
    }
    
    /**
     * RemoveComic method to remove a comic from the system
     * After removal of the object at the lower tier it will check if the specific series is empty and will delete it
     * @param comic comic to remove from the system
     */
    public void removeComic(Comic comic) {
        if (series.containsKey(comic.getSeries())) {
            System.out.println("Series Found");
            Series serie = series.get(comic.getSeries());
            serie.removeComic(comic);
            if (serie.getChildren().size() == 0) {
                series.remove(serie.getName());
            }
        }
    }
    
    /**
     * getComicCount() method to get the number of comics under publisher
     * Scans the entire TreeMap and totals the count for every series
     * @return i (int)
     */
    public int getComicCount() {
        int i = 0;
        for (Series item : series.values()) {
            i += item.getComicCount();
        }
        return i;
    }

    /**
     * getComics() method to get every comic under publisher
     * Scans the entire TreeMap and concatenates every comic in every series to an ArrayList
     * @return bundledComics (ArrayList<Comic>)
     */
    public ArrayList<Comic> getComics() {
        ArrayList<Comic> bundledComics = new ArrayList<Comic>();
        for (Series item : series.values()) {
            bundledComics.addAll(item.getComics());
        }
        return bundledComics;
    }
    
    /**
     * getter for specific series in TreeMap
     * @param seriesName key of Collection item to pull series from
     * @return series returns a series of type Collection(the overarching interface)
     */
     public Collection getChild(String seriesName) {
        return series.get(seriesName);
    }
    
    /**
     * getter for entire TreeMap of series in publisher
     * @return series (TreeMap<String, Series)
     */
    public TreeMap<String, Series> getChildren() {
        return series;
    }
    
    /**
     * getter for the name of the publisher
     * @return name (String)
     */
    public String getName() {
        return name;
    }

    /**
     * toString() method to represent publisher in the system by returning name
     * @return name (String)
     */
    @Override
    public String toString(){
        return name;
    }
    
    /**
     * visitor method to implement visitor pattern
     * @param visitor Specific CollectionVisitor to implement pattern. Specific to class
     */
    @Override
    public void accept(CollectionVisitor visitor) {
        visitor.visit(this);
    }

}

