package com.comix.model.collection.visitor.strategy;

import com.comix.model.collection.Volume;
import com.comix.model.comic.Comic;
import com.comix.model.comic.GradeDecorator;

public class GradeComicSearch implements SearchType{
    @Override
    public boolean searchBy(Comic comic) {
        if (comic instanceof GradeDecorator<?>)
            return true;
        return false;
    }
    @Override
    public boolean searchVolume(Volume volume) {
        return false;
    }
}
