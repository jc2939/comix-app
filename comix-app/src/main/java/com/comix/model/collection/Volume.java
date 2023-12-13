package com.comix.model.collection;

import java.util.ArrayList;
import java.util.TreeMap;

import com.comix.model.collection.visitor.CollectionVisitor;
import com.comix.model.collection.visitor.Visitable;
import com.comix.model.comic.Comic;

/** Class to represent the Volume composite tier of the project.
 * Volume is a composite and holds a treemap of Issues, the next lower composite
*/
public class Volume implements Collection, Visitable {
    private TreeMap<String, Issue> issues;
    private String name;

    /**
     * Main Constructor for Volume 
     * @param issues TreeMap<String, Issue> representing the collection of issues under a Volume
     * @param name String representing name of Volume
     */
    public Volume(TreeMap<String, Issue> issues, String name) {
        this.issues = issues;
        this.name = name;
    }

    /**
     * Alternate Constructor for Volume
     * Instantiates an empty TreeMap of Issues as a basline
     * @param name Name of the Volume
     */
    public Volume(String name) {
        this.name = name;
        this.issues = new TreeMap<String, Issue>();
    }
    
    /**
     * getValue() function to get the value of all the comics under a certain composite
     * Volume calls getValue() in every Issue in its TreeMap
     * @return i (double)
     */
    public double getValue() {
        int i = 0;
        for (Issue item : issues.values()) {
            i += item.getValue();
        }
        return i;
    }
    
    /**
     * Remove method to remove entries from map
     * @param issue  representing a issue that you want to be removed from the TreeMap
     */
    public void remove(Collection issue) {
        issues.remove(issue.getName());
    }
    
    /**
     * add method to add entries to the map
     * @param issue representing a issue that you want to be add to the TreeMap
     */
    public void add(Collection issue) {
        issues.put(issue.getName(), ((Issue) issue));
    }

    /**
     *AddComic method to add a comic to a collection. 
     *If publisher,series,Volume,Issue field don't exist in the system then it will create them and add the comic appropriately
     *@param comic comic to be added into the system Contains all the necessarry information to place itself
     *@return comic (Comic)
     */
    public Comic addComic(Comic comic) {
        if (!issues.containsKey(comic.getIssue())) {
            issues.put(comic.getIssue(), new Issue(comic.getIssue()));
        }
        issues.get(comic.getIssue()).addComic(comic);
        return comic;
    }

    /**
     * RemoveComic method to remove a comic from the system
     * After removal of the object at the lower tier, it will check if the specific issue is empty and will delete it
     * @param comic comic to remove from the system
     */
    public void removeComic(Comic comic) {
        if (issues.containsKey(comic.getIssue())) {
            System.out.println("Issue Found");
            Issue issue = issues.get(comic.getIssue());
            issue.removeComic(comic);
            if (issue.getComicChildren().size() == 0) {
                issues.remove(issue.getName());
            }
        }
    }

    /**
     * getComicCount() method to get the number of comics under Volume
     * Scans the entire TreeMap and totals the count of comics for every issue
     * @return i (int)
     */
    public int getComicCount() {
        int i = 0;
        for (Issue item : issues.values()) {
            i += item.getComicCount();
        }
        return i;
    }

    /**
     * getComics() method to get every comic under Volume
     * Scans the entire TreeMap and concatenates every comic in every Issue to an ArrayList
     * @return bundledComics (ArrayList<Comic>)
     */
    public ArrayList<Comic> getComics() {
        ArrayList<Comic> bundledComics = new ArrayList<Comic>();
        for (Issue item : issues.values()) {
            bundledComics.addAll(item.getComics());
        }
        return bundledComics;
    }

    /**
     * getter for specific issue in TreeMap
     * @param issueName key of Collection item to pull issue from
     * @return issue returns a issue of type Collection(the overarching interface)
     */
    public Collection getChild(String issueName) {
        return issues.get(issueName);
    }
    
    /**
     * getter for entire TreeMap of issues in volume
     * @return issues (TreeMap<String, Series)
     */
    public TreeMap<String, Issue> getChildren() {
        return issues;
    }

    /**
     * getter for the name of the volume
     * @return name (String)
     */
    public String getName() {
        return name;
    }

    /**
     * toString() method to represent volume in the system by returning name
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