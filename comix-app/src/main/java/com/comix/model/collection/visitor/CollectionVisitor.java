package com.comix.model.collection.visitor;

import java.util.ArrayList;

import com.comix.model.collection.*;
import com.comix.model.collection.visitor.strategy.SearchType;
import com.comix.model.comic.Comic;

public interface CollectionVisitor {
    public void setStrategy(SearchType searchType);

    public PersonalCollection visit(PersonalCollection collection);

    public Publisher visit(Publisher publisher);

    public Series visit(Series series);

    public Volume visit(Volume volume);

    public Issue visit(Issue issue);

    public ArrayList<Comic> getResults();
}
