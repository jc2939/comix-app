package com.comix.model.collection.visitor.strategy;

import com.comix.model.collection.Volume;
import com.comix.model.comic.Comic;

public class PublicationDateSearch implements SearchType {
    private boolean exactMatch;
    private String date;

    public PublicationDateSearch(boolean exactMatch, String date)
    {
        this.exactMatch = exactMatch;
        this.date = date;
    }

    @Override
    public boolean searchBy(Comic comic) {
        if (exactMatch)
        {
            if (comic.getPublicationDate().equalsIgnoreCase(date))
                return true;
            else
                return false;
        }
        else
            if (comic.getPublicationDate().toLowerCase().contains(date.toLowerCase()))
                return true;
            else
                return false;
    }

    @Override
    public boolean searchVolume(Volume volume) {
        return false;
    }
}
