package com.comix.model.collection.visitor.strategy;

import com.comix.model.collection.Volume;
import com.comix.model.comic.Comic;

public class ComicTitleSearch implements SearchType {

    private boolean exactMatch;
    private String title;

    public ComicTitleSearch(boolean exactMatch, String title)
    {
        this.exactMatch = exactMatch;
        this.title = title;
    }

    @Override
    public boolean searchBy(Comic comic) {
        if (exactMatch)
        {
            if (comic.getComicTitle().equalsIgnoreCase(title))
                return true;
            else
                return false;
        }
        else
            if (comic.getComicTitle().toLowerCase().contains(title.toLowerCase()))
                return true;
            else
                return false;
    }

    @Override
    public boolean searchVolume(Volume volume) {
        return false;
    }
    
}
