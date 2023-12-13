package com.comix.model.collection.visitor;

import java.util.ArrayList;

import com.comix.model.collection.*;
import com.comix.model.collection.visitor.strategy.SearchType;
import com.comix.model.comic.Comic;

public class PersonalCollectionVisitor implements CollectionVisitor {
    private SearchType searchType;
    private ArrayList<Comic> results = new ArrayList<>();

    public void setStrategy(SearchType searchType) {
        this.searchType = searchType;
    }

    @Override
    public PersonalCollection visit(PersonalCollection collection) {
        results.clear();
        for (Publisher publisher : collection.getChildren().values()) {
            publisher.accept(this);
        }
        return collection;
    }

    @Override
    public Publisher visit(Publisher publisher) {
        for (Series series : publisher.getChildren().values()) {
            series.accept(this);
        }
        return publisher;
    }

    @Override
    public Series visit(Series series) {
        for (Volume volume : series.getChildren().values()) {
            if (searchType.searchVolume(volume))
            {
                for (Comic comic: volume.getComics())
                    results.add(comic);
            }
            volume.accept(this);
        }
        return series;
    }

    @Override
    public Volume visit(Volume volume) {
        for (Issue issue : volume.getChildren().values()) {
            issue.accept(this);
        }
        return volume;
    }

    @Override
    public Issue visit(Issue issue) {
        ArrayList<Comic> listOfComics = issue.getComics();
        for (Comic comic : listOfComics) {
            if (searchType.searchBy(comic))
                results.add(comic);
        }
        return issue;
    }

    public ArrayList<Comic> getResults() {
        return results;
    }
}
