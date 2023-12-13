package com.comix.model.collection.visitor.strategy;

import com.comix.model.collection.Volume;
import com.comix.model.comic.Comic;

public interface SearchType {
    public boolean searchBy(Comic comic);
    public boolean searchVolume(Volume volume);
}
