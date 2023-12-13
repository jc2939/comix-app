package com.comix.controller.action;

import java.util.ArrayList;

import com.comix.model.collection.PersonalCollection;
import com.comix.model.collection.visitor.CollectionVisitor;
import com.comix.model.collection.visitor.PersonalCollectionVisitor;
import com.comix.model.collection.visitor.strategy.CharacterSearch;
import com.comix.model.collection.visitor.strategy.CreatorNameSearch;
import com.comix.model.collection.visitor.strategy.DescriptionSearch;
import com.comix.model.collection.visitor.strategy.SearchType;
import com.comix.model.collection.visitor.strategy.SeriesTitleSearch;
import com.comix.model.comic.Comic;
import com.comix.model.comic.comicComparators.DefaultComparator;
import com.comix.model.comic.comicComparators.PublicationDateComparator;
import com.comix.persistence.PersonalCollectionDao;

public class SearchCollectionAction implements Action<ActionResult<ArrayList<Comic>>> {
    private static final String COMIC_SEARCH_NO_RESULTS_MESSAGE = "No results found.";
    private static final String COMIC_SEARCH_RESULTS = "Here is your list of comics:";

    private String username;
    private PersonalCollectionDao pcDao;
    private CollectionVisitor visitor;
    private SearchType strategy;
    private SortType sortType;

    /**
     * Constructor for a SearchCollectionAction.
     * 
     * @param username   The string username of the user searching their collection.
     * @param searchTerm The search term to be used.
     */
    public SearchCollectionAction(String username, SortType sortType, SearchField searchType, Boolean exactMatch,
            String searchTerm,
            PersonalCollectionDao cDao) {
        this.username = username;
        this.pcDao = cDao;
        this.sortType = sortType;
        this.visitor = new PersonalCollectionVisitor();
        switch (searchType) {
            case SERIES_TITLE:
                strategy = new SeriesTitleSearch(exactMatch, searchTerm);
                break;
            case PRINCIPAL_CHARACTERS:
                strategy = new CharacterSearch(exactMatch, searchTerm);
                break;
            case CREATOR_NAMES:
                strategy = new CreatorNameSearch(exactMatch, searchTerm);
                break;
            case DESCRIPTION:
                strategy = new DescriptionSearch(exactMatch, searchTerm);
                break;
        }
        visitor.setStrategy(strategy);
    }

    /**
     * Executes a user's action to search their personal collection.
     * 
     * @return The result of the action, containing the list of comics found
     *         matching the search term.
     */
    public ActionResult<ArrayList<Comic>> execute() {
        ArrayList<Comic> searchResult = pcDao.searchCollection(username, visitor);

        // sort the results based on the sort type enum
        switch (sortType) {
            case DEFAULT:
                DefaultComparator comparator = new DefaultComparator();
                searchResult.sort(comparator);
                break;
            case PUBLICATION_DATE:
                PublicationDateComparator pubComparator = new PublicationDateComparator();
                searchResult.sort(pubComparator);
                break;
        }

        if (searchResult.size() == 0) {
            return new ActionResult<ArrayList<Comic>>(false, searchResult, COMIC_SEARCH_NO_RESULTS_MESSAGE);
        } else {
            return new ActionResult<ArrayList<Comic>>(true, searchResult, COMIC_SEARCH_RESULTS);
        }
    }
}
