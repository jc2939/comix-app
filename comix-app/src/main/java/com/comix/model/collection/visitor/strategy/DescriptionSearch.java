package com.comix.model.collection.visitor.strategy;

import com.comix.model.collection.Volume;
import com.comix.model.comic.Comic;

public class DescriptionSearch implements SearchType {
    private boolean exactMatch;
    private String description;

    public DescriptionSearch(boolean exactMatch, String description)
    {
        this.exactMatch = exactMatch;
        this.description = description;
    }

    @Override
    public boolean searchBy(Comic comic) {
        if (exactMatch)
        {
            if (comic.getDescription().equalsIgnoreCase(description))
                return true;
            else
                return false;
        }
        else
            if (comic.getDescription().toLowerCase().contains(description.toLowerCase()))
                return true;
            else
                return false;
    }

    @Override
    public boolean searchVolume(Volume volume) {
        return false;
    }
    
}
