package com.comix.model.collection;

import com.comix.model.collection.visitor.CollectionVisitor;
import com.comix.model.collection.visitor.Visitable;

import com.comix.model.comic.Comic;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.TreeMap;
/** Class to represent the Issue tier of the project.
 * Issue is a composite and holds a treemap of comics
*/
public class Issue implements Collection, Visitable {

    private TreeMap<String, Comic> comics;
    private String issueNumber;

    /**
     * Main Constructor for Personal Collection 
     * @param comics TreeMap<String, Comic> representing the collection of comics under a Issue
     * @param name String representing name of Personal Collection
     */
    public Issue(TreeMap<String, Comic> comics, String issueNumber) {
        this.comics = comics;
        this.issueNumber = issueNumber;
    }

    /**
     * Alternate Constructor for Issue
     * Instantiates an empty TreeMap of Comics as a basline
     * @param issueNumber the Issues number
     */
    public Issue(String issueNumber) {
        this.issueNumber = issueNumber;
        this.comics = new TreeMap<String, Comic>();
    }

    /**
     * getValue() function to get the value of all the comics in the TreeMap
     * Issue just parses over its map of comics and calls getvalue to total it
     * @return i (double)
     */
    public double getValue() {
        int i = 0;
        for (Comic item : comics.values()) {
            i += item.getValue();
        }
        return i;
    }

    //stubbed method
    public void remove(Collection issue) {
        //
    }
    
    //stubbed method
    public void add(Collection issue) {
        //
    }
    
    /**
     * Adds the given comic to the map of comics
     * @param comic the comic we will 
     * @return comic (Comic)
     */
    public Comic addComic(Comic comic) {
        comics.put(comic.getComicID(), comic);
        return comic;
    }
    
    /**
     * Removes a comic from the Map of comics
     * @param comic the comic we will remove
     */
    public void removeComic(Comic comic) {
        System.out.println("Comic Found");
        System.out.println(comics.get(comic.getComicID()));
        System.out.println("comic:"+comic.getComicID());
        System.out.println(comics);
        // comic=comic.copy();
        // comics.remove(comic.getComicID());
        System.out.println("Comic:"+comic.getComicID());

        java.util.Collection<Comic> values= comics.values();

        for (Comic comic2 : values) {
            if (comic.getComicTitle().equals(comic2.getComicTitle())&& comic.getIssue().equals(comic2.getIssue())&& comic.getPublisher().equals(comic2.getPublisher())&&comic.getSeries().equals(comic2.getSeries())) {
                comics.remove(comic2.getComicID());
                System.out.println(comic2.getComicID());
            }
        }
        ;

    }
    /**
     * gets the size of the map of comics
     * @return comics.size() (int)
     */
    public int getComicCount() {
        return comics.size();
    }

    /**
     * returns an Arraylist of comics from the TreeMap
     * @return ArrayList<Comic> (ArrayList<Comic>)
     */
    public ArrayList<Comic> getComics() {
        return new ArrayList<>(comics.values());
    }

    //stubbed
    public Collection getChild(String issueName) {
        return new Issue("null");
    }

    // stubbed
    public TreeMap<String, Issue> getChildren() {
        return new TreeMap<>();
    }
    
    /**
     * getter for the treemap of comics
     * @return comics (TreeMap<String, Comic>)
     */
    public TreeMap<String, Comic> getComicChildren(){
        return comics;
    }

    /**
     * getter for the name of the issueNumber
     * @return name (String)
     */
    public String getName() {
        return issueNumber;
    }

    /**
     * toString() method to represent Issues in the system by returning number
     * @return name (String)
     */
    @Override
    public String toString(){
        return(issueNumber);
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
