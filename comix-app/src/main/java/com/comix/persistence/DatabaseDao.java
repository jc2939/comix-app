package com.comix.persistence;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.comix.model.collection.visitor.CollectionVisitor;
import com.comix.model.collection.visitor.PersonalCollectionVisitor;
import com.comix.model.comic.Comic;

public interface DatabaseDao {
    public ArrayList<Comic> searchDatabase(PersonalCollectionVisitor visitor);
}
