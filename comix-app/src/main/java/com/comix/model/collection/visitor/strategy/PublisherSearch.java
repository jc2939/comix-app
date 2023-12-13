package com.comix.model.collection.visitor.strategy;

import com.comix.model.collection.Volume;
import com.comix.model.comic.Comic;

public class PublisherSearch implements SearchType {
    private boolean exactMatch;
    private String publisher;

    public PublisherSearch(boolean exactMatch, String publisher)
    {
        this.exactMatch = exactMatch;
        this.publisher = publisher;   
    }

    @Override
    public boolean searchBy(Comic comic)
    {
        if (exactMatch)
        {
            if (comic.getPublisher().equalsIgnoreCase(publisher))
                return true;
            else
                return false;
        }
        else
            if (comic.getPublisher().toLowerCase().contains(publisher.toLowerCase()))
                return true;
            else
                return false;
    }

    @Override
    public boolean searchVolume(Volume volume) {
        return false;
    }
    
}