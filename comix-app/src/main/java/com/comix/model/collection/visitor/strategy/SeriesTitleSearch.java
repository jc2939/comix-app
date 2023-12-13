package com.comix.model.collection.visitor.strategy;

import com.comix.model.collection.Volume;
import com.comix.model.comic.Comic;

public class SeriesTitleSearch implements SearchType
{
    private                                                                  boolean exactMatch;
    private String title;
    public SeriesTitleSearch(boolean exactMatch, String title)
    {
        this.exactMatch = exactMatch;
        this.title = title;
    }

    @Override
    public boolean searchBy(Comic comic) {
        if (exactMatch)
        {
            if (comic.getSeries().equalsIgnoreCase(title))
                return true;
            else
                return false;
        }
        else
            if (comic.getSeries().toLowerCase().contains(title.toLowerCase()))
                return true;
            else
                return false;
    }

    @Override
    public boolean searchVolume(Volume volume) {
        return false;
    }
    
}