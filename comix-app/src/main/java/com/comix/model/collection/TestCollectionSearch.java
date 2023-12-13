package com.comix.model.collection;

import com.comix.model.collection.visitor.CollectionVisitor;
import com.comix.model.collection.visitor.PersonalCollectionVisitor;
import com.comix.model.collection.visitor.strategy.*;
import com.comix.model.comic.Comic;
import com.comix.model.comic.ConcreteComic;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class TestCollectionSearch {
    public static void main(String[] args) {
        TreeMap<String, Publisher> publisherTreeMap = new TreeMap<>();
        PersonalCollection collection = new PersonalCollection(publisherTreeMap, "coolDude1");
        TreeMap<String, ConcreteComic> comics = new TreeMap<>();
        ArrayList<String> spidermanCreators = new ArrayList<>();
        ArrayList<String> batmanCharacters = new ArrayList<>();
        spidermanCreators.add("Stan Lee");
        spidermanCreators.add("Steve Ditko");
        batmanCharacters.add("Bruce Wayne");
        batmanCharacters.add("Dick Grayson");
        batmanCharacters.add("Jason Todd");

        comics.put("Batman - Death in the Family - Part 1", new ConcreteComic(
                "DC Comics",
                "Batman",
                "Death in the Family",
                "The Diplomat's Son",
                "1988",
                null,
                null,
                batmanCharacters,
                null,
                null
        ));

        comics.put("Secret Wars", new ConcreteComic(
                "Marvel",
                "Spider-Man",
                "The Amazing Spider-Man",
                "17",
                "1999",
                null,
                spidermanCreators,
                null,
                null,
                null
        ));

        comics.put("HellBoy", new ConcreteComic(
                "Action Comics",
                "HellBoy",
                "1",
                "1",
                "2004",
                null,
                null,
                null,
                null,
                null
        ));

        comics.put("Spawn", new ConcreteComic(
                "Todd MacFarland",
                "Spawn",
                "2",
                "10",
                "1989",
                null,
                null,
                null,
                "Very Cool Comic",
                null
        ));

        for(Comic comic : comics.values()) {
            collection.addComic(comic);
        }

        PersonalCollectionVisitor visitor = new PersonalCollectionVisitor();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Please select 'true' for exact match or 'false' for partial match: ");
        String exactMatchDecision = scanner.nextLine();
        boolean exactMatch = Boolean.parseBoolean(exactMatchDecision);

        System.out.println("What parameter would you like to search by?");
        System.out.println("1. Search By Creators");
        System.out.println("2. Search By Series Title");
        System.out.println("3. Search By Description");
        System.out.println("4. Search By Characters");
        System.out.println("Please enter the number of your selection: ");

        int searchDecision = Integer.parseInt(scanner.nextLine());

        switch (searchDecision) {
            case 1:
                System.out.println("Please type creator name: ");
                String query = scanner.nextLine();
                SearchType creatorNameSearch = new CreatorNameSearch(exactMatch, query);
                visitor.setStrategy(creatorNameSearch);
                break;
            case 2:
                System.out.println("Please type series title: ");
                query = scanner.nextLine();
                SearchType titleNameSearch = new SeriesTitleSearch(exactMatch, query);
                visitor.setStrategy(titleNameSearch);
                break;
            case 3:
                System.out.println("Please enter description: ");
                query = scanner.nextLine();
                SearchType descriptionSearch = new DescriptionSearch(exactMatch, query);
                visitor.setStrategy(descriptionSearch);
                break;
            case 4:
                System.out.println("Please type character name: ");
                query = scanner.nextLine();
                SearchType characterSearch = new CharacterSearch(exactMatch, query);
                visitor.setStrategy(characterSearch);
                break;
            default:
                System.out.println("Invalid choice, please select a valid option.");
                break;
        }
        collection.accept(visitor);
        System.out.println(visitor.getResults());
    }
}
