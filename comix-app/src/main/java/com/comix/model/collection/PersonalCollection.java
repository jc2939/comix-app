package com.comix.model.collection;

import java.util.ArrayList;
import java.util.TreeMap;

import com.comix.model.collection.visitor.CollectionVisitor;
import com.comix.model.collection.visitor.Visitable;
import com.comix.model.comic.Comic;

/** Class to represent the Personal Collection composite tier of the project.
 * Personal Collection is a composite and holds a treemap of Publishers, the next lower composite
*/
public class PersonalCollection implements Collection, Visitable {
    private TreeMap<String, Publisher> publishers;
    private String userName;

    /**
     * Main Constructor for Personal Collection 
     * @param issues TreeMap<String, Publisher> representing the collection of Publishers under a Personal Collection
     * @param name String representing name of Personal Collection
     */
    public PersonalCollection(TreeMap<String, Publisher> publishers, String userName) {
        this.publishers = publishers;
        this.userName = userName;
    }

   /**
     * Alternate Constructor for Series
     * Instantiates an empty TreeMap of Publishers as a basline
     * @param name Name of the Personal Collection
     */
    public PersonalCollection(String userName) {
        this.userName = userName;
        this.publishers = new TreeMap<String, Publisher>();
    }
    /**
     * getValue() function to get the value of all the comics under a certain composite
     * Personal Collections calls getValue() in every Publisher in its TreeMap
     * @return i (double)
     */
    public double getValue() {
        int i = 0;
        for (Publisher item : publishers.values()) {
            i += item.getValue();
        }
        return i;
    }

    /**
     * Remove method to remove entries from map
     * @param publisher  representing a publisher that you want to be removed from the TreeMap
     */
    public void remove(Collection publisher) {
        publishers.remove(publisher.getName());
    }

    /**
     * add method to add entries to the map
     * @param publisher representing a publisher that you want to be add to the TreeMap
     */
    public void add(Collection publisher) {
        publishers.put(publisher.getName(), ((Publisher) publisher));
    }

    /**
     *AddComic method to add a comic to a collection. 
     *If publisher,series,Volume,Issue field don't exist in the system then it will create them and add the comic appropriately
     *@param comic comic to be added into the system Contains all the necessarry information to place itself
     *@return comic (Comic)
     */
    public Comic addComic(Comic comic) {
        if (!publishers.containsKey(comic.getPublisher())) {
            publishers.put(comic.getPublisher(), new Publisher(comic.getPublisher()));
        }
        publishers.get(comic.getPublisher()).addComic(comic);
        return comic;
    }

    /**
     * RemoveComic method to remove a comic from the system
     * After removal of the object at the lower tier, it will check if the specific Publisher is empty and will delete it
     * @param comic comic to remove from the system
     */
    public void removeComic(Comic comic) {
        if (publishers.containsKey(comic.getPublisher())) {
            System.out.println("Publisher found");
            Publisher publisher = publishers.get(comic.getPublisher());
            publisher.removeComic(comic);
            if (publisher.getChildren().size() == 0) {
                publishers.remove(publisher.getName());
            }
        }

    }

    /**
     * getComicCount() method to get the total number of comics under Personal Collection
     * Scans the entire TreeMap and totals the count of comics for every Publisher
     * @return i (int)
     */
    public int getComicCount() {
        int i = 0;
        for (Publisher item : publishers.values()) {
            i += item.getComicCount();
        }
        return i;
    }

    /**
     * getComics() method to get every comic under Personal Collection
     * Scans the entire TreeMap and concatenates every comic in every Publisher to an ArrayList
     * @return bundledComics (ArrayList<Comic>)
     */
    public ArrayList<Comic> getComics() {
        ArrayList<Comic> bundledComics = new ArrayList<Comic>();
        for (Publisher item : publishers.values()) {
            bundledComics.addAll(item.getComics());
        }
        return bundledComics;
    }

    /**
     * getter for specific Publisher in TreeMap
     * @param publisherName key of Collection item to pull Publisher from
     * @return Publisher returns a Publisher of type Collection(the overarching interface)
     */
    public Collection getChild(String publisherName) {
        return publishers.get(publisherName);
    }

    /**
     * getter for entire TreeMap of Publishers in a Personal Collection
     * @return publishers (TreeMap<String, Publisher)
     */
    public TreeMap<String, Publisher> getChildren() {
        return publishers;
    }


    /**
     * getter for the name of the Personal Collection
     * @return name (String)
     */
    public String getName() {
        return userName;
    }

    /**
     * toString() method to represent Personal Collection in the system by returning name
     * @return name (String)
     */ 
    @Override
    public String toString(){
        return userName;
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