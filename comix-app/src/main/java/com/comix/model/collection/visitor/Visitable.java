package com.comix.model.collection.visitor;

public interface Visitable {
    public void accept(CollectionVisitor visitor);
}
