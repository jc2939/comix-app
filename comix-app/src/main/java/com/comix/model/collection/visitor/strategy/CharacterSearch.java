package com.comix.model.collection.visitor.strategy;

import com.comix.model.collection.Volume;
import com.comix.model.comic.Comic;

public class CharacterSearch implements SearchType {
    private boolean exactMatch;
    private String character;

    public CharacterSearch(boolean exactMatch, String character) {
        this.exactMatch = exactMatch;
        this.character = character;
    }

    @Override
    public boolean searchBy(Comic comic) {
        for (String principle: comic.getPrincipleCharacters()) {
            if (exactMatch) {
                if (principle.equalsIgnoreCase(character)) {
                    return true;
                }
            }
            else {
                if (principle.toLowerCase().contains(character.toLowerCase())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean searchVolume(Volume volume) {
        return false;
    }
}
