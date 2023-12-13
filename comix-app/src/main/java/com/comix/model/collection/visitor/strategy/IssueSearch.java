package com.comix.model.collection.visitor.strategy;

import com.comix.model.collection.Volume;
import com.comix.model.comic.Comic;

public class IssueSearch implements SearchType{

    private boolean exactMatch;
    private String issue;

    public IssueSearch(boolean exactMatch, String issue) {
        this.exactMatch = exactMatch;
        this.issue = issue;
    }

    @Override
    public boolean searchBy(Comic comic) {
        if (exactMatch)
        {
            if (comic.getIssue().equalsIgnoreCase(issue))
                return true;
            else
                return false;
        }
        else
            if (comic.getIssue().toLowerCase().contains(issue.toLowerCase()))
                return true;
            else
                return false;
    }
    @Override
    public boolean searchVolume(Volume volume) {
        return false;
    }
    
}
