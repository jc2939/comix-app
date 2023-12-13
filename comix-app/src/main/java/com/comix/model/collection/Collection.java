package com.comix.model.collection;

import java.util.ArrayList;
import java.util.Map;

import com.comix.model.collection.visitor.PersonalCollectionVisitor;
import com.comix.model.comic.Comic;

public interface Collection {
    double getValue();

    void add(Collection item);

    void remove(Collection item);

    Collection getChild(String name);

    Map<String, ? extends Collection> getChildren();

    ArrayList<Comic> getComics();
    Comic addComic(Comic comic);

    void removeComic(Comic comic);

    int getComicCount();

    String getName();
}
