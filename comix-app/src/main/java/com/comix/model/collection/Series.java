package com.comix.model.collection;

import java.util.ArrayList;
import java.util.TreeMap;

import com.comix.model.collection.visitor.CollectionVisitor;
import com.comix.model.collection.visitor.Visitable;
import com.comix.model.comic.Comic;

/** Class to represent the Series composite tier of the project.
 * Series is a composite and holds a treemap of Volumes, the next lower composite
*/
public class Series implements Collection, Visitable{
    private TreeMap<String, Volume> volumes;
    private String name;

    /**
     * Main Constructor for Series 
     * @param issues TreeMap<String, Volume> representing the collection of Volumes under a Series
     * @param name String representing name of Series
     */
    public Series(TreeMap<String, Volume> volumes, String name) {
        this.volumes = volumes;
        this.name = name;
    }
   
    /**
     * Alternate Constructor for Series
     * Instantiates an empty TreeMap of Volumes as a basline
     * @param name Name of the Series
     */
    public Series(String name) {
        this.name = name;
        this.volumes = new TreeMap<String, Volume>();
    }

    /**
     * getValue() function to get the value of all the comics under a certain composite
     * Series calls getValue() in every Volume in its TreeMap
     * @return i (double)
     */
    public double getValue() {
        int i = 0;
        for (Volume item : volumes.values()) {
            i += item.getValue();
        }
        return i;
    }

    /**
     * Remove method to remove entries from map
     * @param volume  representing a Volume that you want to be removed from the TreeMap
     */
    public void remove(Collection volume) {
        volumes.remove(volume.getName());
    }

    /**
     * add method to add entries to the map
     * @param volume representing a volume that you want to be add to the TreeMap
     */
    public void add(Collection volume) {
        volumes.put(volume.getName(), ((Volume) volume));
    }
    
    /**
     *AddComic method to add a comic to a collection. 
     *If publisher,series,Volume,Issue field don't exist in the system then it will create them and add the comic appropriately
     *@param comic comic to be added into the system Contains all the necessarry information to place itself
     *@return comic (Comic)
     */
    public Comic addComic(Comic comic) {
        if (!volumes.containsKey(comic.getVolume())) {
            volumes.put(comic.getVolume(), new Volume(comic.getVolume()));
        }
        volumes.get(comic.getVolume()).addComic(comic);
        return comic;
    }

    /**
     * RemoveComic method to remove a comic from the system
     * After removal of the object at the lower tier, it will check if the specific Volume is empty and will delete it
     * @param comic comic to remove from the system
     */
    public void removeComic(Comic comic) {
        if (volumes.containsKey(comic.getVolume())) {
            System.out.println("Volume Found");
            Volume volume = volumes.get(comic.getVolume());
            volume.removeComic(comic);
            if (volume.getChildren().size() == 0) {
                volumes.remove(volume.getName());
            }
        }
    }
    
    /**
     * getComicCount() method to get the number of comics under Series
     * Scans the entire TreeMap and totals the count of comics for every Volume
     * @return i (int)
     */

    public int getComicCount() {
        int i = 0;
        for (Volume item : volumes.values()) {
            i += item.getComicCount();
        }
        return i;
    }

    /**
     * getComics() method to get every comic under Series
     * Scans the entire TreeMap and concatenates every comic in every Volume to an ArrayList
     * @return bundledComics (ArrayList<Comic>)
     */
    public ArrayList<Comic> getComics() {
        ArrayList<Comic> bundledComics = new ArrayList<Comic>();
        for (Volume item : volumes.values()) {
            bundledComics.addAll(item.getComics());
        } 
        return bundledComics;
    }

    /**
     * getter for specific Volume in TreeMap
     * @param volumename key of Collection item to pull Volume from
     * @return Volume returns a Volume of type Collection(the overarching interface)
     */
    public Collection getChild(String volumeName) {
        return volumes.get(volumeName);
    }

    /**
     * getter for entire TreeMap of Volumes in a Series
     * @return volumes (TreeMap<String, Volume)
     */
    public TreeMap<String, Volume> getChildren() {
        return volumes;
    }

    /**
     * getter for the name of the Series
     * @return name (String)
     */
    public String getName() {
        return name;
    }
    
    /**
     * toString() method to represent Series in the system by returning name
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