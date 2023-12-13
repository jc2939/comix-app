package com.comix.model.collection.visitor.strategy;

import com.comix.model.collection.Volume;
import com.comix.model.comic.Comic;

public class CreatorNameSearch implements SearchType {
    private boolean exactMatch;
    private String name;

    public CreatorNameSearch(boolean exactMatch, String name)
    {
        this.exactMatch = exactMatch;
        this.name = name;
    }

    @Override
    public boolean searchBy(Comic comic) {
        boolean result = false;
        for (String creator: comic.getCreators())
        {
            if (exactMatch)
            {
                if (creator.equalsIgnoreCase(name))
                {
                    result = true;
                }
            }
            else
            {
                if (creator.toLowerCase().contains(name.toLowerCase()))
                {
                    result = true;
                }
            }
        }
        return result;
    }

    @Override
    public boolean searchVolume(Volume volume) {
        return false;
    }
    
}
