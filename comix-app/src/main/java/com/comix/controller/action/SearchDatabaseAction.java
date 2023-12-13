package com.comix.controller.action;

import java.util.ArrayList;

import com.comix.model.collection.visitor.PersonalCollectionVisitor;
import com.comix.model.collection.visitor.strategy.CharacterSearch;
import com.comix.model.collection.visitor.strategy.ComicTitleSearch;
import com.comix.model.collection.visitor.strategy.CreatorNameSearch;
import com.comix.model.collection.visitor.strategy.DescriptionSearch;
import com.comix.model.collection.visitor.strategy.IssueSearch;
import com.comix.model.collection.visitor.strategy.PublicationDateSearch;
import com.comix.model.collection.visitor.strategy.PublisherSearch;
import com.comix.model.collection.visitor.strategy.SearchType;
import com.comix.model.collection.visitor.strategy.SeriesTitleSearch;
import com.comix.model.comic.Comic;
import com.comix.model.comic.comicComparators.DefaultComparator;
import com.comix.model.comic.comicComparators.PublicationDateComparator;
import com.comix.persistence.DatabaseDao;

public class SearchDatabaseAction implements Action<ActionResult<ArrayList<Comic>>> {
    private static final String COMIC_SEARCH_NO_RESULTS_MESSAGE = "No results found.";
    private static final String COMIC_SEARCH_RESULTS = "Here is the list of comics:";

    private DatabaseDao dbDao;
    private PersonalCollectionVisitor visitor;
    private SearchType strategy;
    private SortType sortType;

    /**
     * Constructor for a SearchDatabaseAction.
     * 
     * @param searchTerm The search term to be used.
     * @param dbDao      The DAO for the database.
     */
    public SearchDatabaseAction(SortType sortType, SearchField searchType, Boolean exactMatch, String searchTerm,
            DatabaseDao dbDao) {
        this.dbDao = dbDao;
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
            case ISSUE_NUMBER:
                strategy = new IssueSearch(exactMatch, searchTerm);
                break;
            case PUBLISHER:
                strategy = new PublisherSearch(exactMatch, searchTerm);
                break;
            case STORY_TITLE:
                strategy = new ComicTitleSearch(exactMatch, searchTerm);
                break;
            case PUBLICATION_DATE:
                strategy = new PublicationDateSearch(exactMatch, searchTerm);
                break;
        }
        visitor.setStrategy(strategy);
    }

    /**
     * Executes a user's action to search the database.
     * 
     * @return The result of the action, containing the list of comics found
     *         matching the search term.
     */
    public ActionResult<ArrayList<Comic>> execute() {
        ArrayList<Comic> searchResult = dbDao.searchDatabase(visitor);
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
