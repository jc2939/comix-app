package com.comix.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import com.comix.controller.action.ActionResult;
import com.comix.controller.action.AddComicAction;
import com.comix.controller.action.BrowseCollectionAction;
import com.comix.controller.action.CreateAccountAction;
import com.comix.controller.action.EditComicAction;
import com.comix.controller.action.GradeComicAction;
import com.comix.controller.action.LoginAction;
import com.comix.controller.action.RemoveComicAction;
import com.comix.controller.action.SearchCollectionAction;
import com.comix.controller.action.SearchDatabaseAction;
import com.comix.controller.action.SearchField;
import com.comix.controller.action.SlabComicAction;
import com.comix.controller.action.SortType;
import com.comix.model.collection.Collection;
import com.comix.model.comic.Comic;
import com.comix.model.comic.ConcreteComic;
import com.comix.model.comic.GradeDecorator;
import com.comix.model.comic.SlabDecorator;
import com.comix.model.user.User;
import com.comix.persistence.DatabaseDao;
import com.comix.persistence.DatabaseFileDao;
import com.comix.persistence.PersonalCollectionDao;
import com.comix.persistence.PersonalCollectionFileDao;
import com.comix.persistence.UserDao;
import com.comix.persistence.UserFileDao;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PTUI {
    // INPUT CONSTANTS
    private static final String LOGIN_INPUT = "login";
    private static final String LOGOUT_INPUT = "logout";
    private static final String CREATE_INPUT = "create";

    private static final String BACK_INPUT = "back";
    private static final String EXIT_INPUT = "exit";

    // Search menu inputs
    private static final String SEARCH_PUBLISHER_INPUT = "publisher";
    private static final String SEARCH_SERIES_INPUT = "series";
    private static final String SEARCH_VOLUME_INPUT = "volume";
    private static final String SEARCH_ISSUE_INPUT = "issue";
    private static final String SEARCH_PUBLICATION_DATE_INPUT = "publication";
    private static final String SEARCH_CREATORS_INPUT = "creators";
    private static final String SEARCH_STORY_TITLE_INPUT = "title";
    private static final String SEARCH_DESCRIPTION_INPUT = "description";
    private static final String SEARCH_CHARACTERS_INPUT = "characters";

    // other menu inputs
    private static final String ADD_INPUT = "add";
    private static final String REMOVE_INPUT = "remove";
    private static final String EDIT_INPUT = "edit";
    private static final String SEARCH_INPUT = "search";
    private static final String MANUAL_ADD_INPUT = "manual";
    private static final String DATABASE_ADD_INPUT = "database";

    // Edit menu inputs
    private static final String EDIT_PUBLISHER_INPUT = "publisher";
    private static final String EDIT_SERIES_INPUT = "series";
    private static final String EDIT_VOLUME_INPUT = "volume";
    private static final String EDIT_ISSUE_INPUT = "issue";
    private static final String EDIT_PUBLICATION_DATE_INPUT = "publication";
    private static final String EDIT_TITLE_INPUT = "title";
    private static final String EDIT_CREATORS_INPUT = "creators";
    private static final String EDIT_PRINCIPLE_CHARACTERS_INPUT = "characters";
    private static final String EDIT_DESCRIPTION_INPUT = "description";
    private static final String EDIT_VALUE_INPUT = "value";
    private static final String EDIT_GRADE_INPUT = "grade";
    private static final String EDIT_SLAB_INPUT = "slab";

    // GENERAL MESSAGES
    private static final String WELCOME_MESSAGE = "Welcome to Comix!";
    private static final String BACK_MESSAGE = "Enter 'back' to return to the previous menu.";
    private static final String INVALID_COMMAND_MESSAGE = "Invalid command. Please enter one of the commands below.";
    private static final String BROWSE_INVALID_INPUT_MESSAGE = "Invalid input. Please enter a number or one of the commands below.";

    // ACCOUNT MESSAGES
    private static final String USERNAME_PROMPT = "Please enter your username:";
    private static final String PASSWORD_PROMPT = "Please enter your password:";
    private static final String CONFIRM_PASSWORD_PROMPT = "Please confirm your password:";
    private static final String PASSWORD_MISMATCH_MESSAGE = "Passwords do not match. Please try again.";

    private ObjectMapper mapper = new ObjectMapper();
    private DatabaseDao dbDao;
    private PersonalCollectionDao pcDao;
    private UserDao userDao;

    // Current user state
    private User currentUser;
    private Collection currentCollection;

    private PersonalCollectionDao personalDao;

    public static void main(String[] args) throws IOException {
        PTUI ptui = new PTUI();
        ptui.run();
    }

    public PTUI() throws IOException {
        // dbDao = new DatabaseDao(mapper);
        pcDao = new PersonalCollectionFileDao(mapper);
        userDao = new UserFileDao(mapper, personalDao);
        dbDao = new DatabaseFileDao(mapper);
        userDao.setPersonalCollectionDao(pcDao);
    }

    public void run() {
        mainMenu();
    }

    public void exit() {
        System.out.println("Exiting...");
        System.exit(0);
    }

    /**
     * Displays the login menu.
     * Allows the user to login, create a new account, or exit the program.
     * Parses the user's input, creates the appropriate action, and executes it.
     * The result of the action is used for feedback and to determine the next menu
     * to display.
     */
    private void mainMenu() {
        while (currentUser == null) {
            System.out.println(WELCOME_MESSAGE);
            System.out.println("Please enter a command:");
            System.out.println(LOGIN_INPUT + " -> Login to your account");
            System.out.println(CREATE_INPUT + " -> Create a new account");
            System.out.println(EXIT_INPUT + " -> Exit program");

            String input = System.console().readLine().toLowerCase();
            switch (input) {
                case LOGIN_INPUT:
                    loginMenu();
                    break;
                case CREATE_INPUT:
                    createAccountMenu();
                    break;
                case EXIT_INPUT:
                    exit();
                    break;
                default:
                    System.out.println(INVALID_COMMAND_MESSAGE);
                    break;
            }
        }
    }

    /**
     * Creates a new account.
     * Prompts the user for a username and password.
     * Parses the user's input, creates the appropriate action, and executes it.
     * The result of the action is used for feedback and to determine the next menu
     * to display.
     */
    public void loginMenu() {
        while (currentUser == null) {
            System.out.println("\n=== Login ===");
            System.out.println(BACK_MESSAGE);
            System.out.println(USERNAME_PROMPT);
            String username = System.console().readLine();
            if (username.equals(BACK_INPUT)) {
                return;
            }
            System.out.println(PASSWORD_PROMPT);
            String password = System.console().readLine();
            if (password.equals(BACK_INPUT)) {
                return;
            }

            LoginAction loginAction = new LoginAction(username, password, userDao);
            ActionResult<User> result = loginAction.execute();

            System.out.println(result.getMessage());
            if (result.getSuccess()) {
                currentUser = result.getResult();
                viewCollectionMenu();
                return;
            }
        }
    }

    /**
     * Creates a new account.
     * Prompts the user for a username and password.
     * Parses the user's input, creates the appropriate action, and executes it.
     * The result of the action is used for feedback and to determine the next menu
     * to display.
     */
    private void createAccountMenu() {
        while (currentUser == null) {
            System.out.println("\n=== Create Account ===");
            System.out.println(BACK_MESSAGE);
            System.out.println(USERNAME_PROMPT);
            String username = System.console().readLine();
            if (username.equals(BACK_INPUT)) {
                return;
            }
            System.out.println(PASSWORD_PROMPT);
            String password = System.console().readLine();
            if (password.equals(BACK_INPUT)) {
                return;
            }
            System.out.println(CONFIRM_PASSWORD_PROMPT);
            String confirmPassword = System.console().readLine();
            if (confirmPassword.equals(BACK_INPUT)) {
                return;
            }
            if (!password.equals(confirmPassword)) {
                System.out.println(PASSWORD_MISMATCH_MESSAGE);
                continue;
            }

            CreateAccountAction createAccountAction = new CreateAccountAction(username, password, userDao);
            ActionResult<User> result = createAccountAction.execute();

            System.out.println(result.getMessage());
            if (result.getSuccess()) {
                System.out.println("Logging in...");
                currentUser = result.getResult();
                viewCollectionMenu();
            }
        }
    }

    /**
     * Displays the collection menu.
     * Allows the user to view and select Publishers in their collection, add a
     * comic, logout, or exit.
     * 
     * Displays the total value of the collection, along with the total number of
     * issues.
     * 
     * Displays a list of the publishers in the collection, displaying their name
     * and the number of comics from that publisher.
     * Allows the user to select a publisher to view.
     * 
     * Parses the user's input, creates the appropriate action, and executes it.
     * The result of the action is used for feedback and to determine the next menu
     * to display.
     */
    private void viewCollectionMenu() {
        while (currentUser != null) {
            // fetch the user's Collection
            BrowseCollectionAction browseCollectionAction = new BrowseCollectionAction(currentUser.getUsername(),
                    pcDao);
            ActionResult<Collection> result = browseCollectionAction.execute();
            if (!result.getSuccess()) {
                System.out.println(result.getMessage());
                return;
            }
            currentCollection = result.getResult();
            ArrayList<Collection> publishers = new ArrayList<Collection>();
            publishers.addAll(currentCollection.getChildren().values());
            System.out.println("\n=== Collection ===");
            System.out.println("Total value: $" + currentCollection.getValue());
            System.out.println("Total issues: " + currentCollection.getComicCount());
            System.out.println("Publishers:");
            for (int i = 0; i < publishers.size(); i++) {
                System.out.println(
                        (i + 1) + ". " + publishers.get(i).getName() + " (" + publishers.get(i).getComicCount() + ")");
            }
            System.out.println("\nPlease enter a number to browse a publisher, or enter a command:");
            System.out.println(ADD_INPUT + " -> Add a comic to your collection");
            System.out.println(SEARCH_INPUT + " -> Search your collection");
            System.out.println(LOGOUT_INPUT + " -> Logout");
            System.out.println(EXIT_INPUT + " -> Exit program");

            String input = System.console().readLine().toLowerCase();
            switch (input) {
                case ADD_INPUT:
                    addComicMenu();
                    break;
                case SEARCH_INPUT:
                    searchCollectionMenu();
                    break;
                case LOGOUT_INPUT:
                    currentUser = null;
                    currentCollection = null;
                    mainMenu();
                case EXIT_INPUT:
                    exit();
                    break;
                default:
                    int index = 0;
                    try {
                        index = Integer.parseInt(input);
                    } catch (NumberFormatException e) {
                        System.out.println(BROWSE_INVALID_INPUT_MESSAGE);
                        continue;
                    }
                    if (index < 1 || index > publishers.size()) {
                        System.out.println(BROWSE_INVALID_INPUT_MESSAGE);
                        continue;
                    }
                    viewPublisherMenu(publishers.get(index - 1));
                    break;
            }
        }
    }

    /**
     * Displays the publisher menu.
     * Allows the user to view and select series belonging to the given publisher,
     * add a comic to their collection, go back, logout, or exit.
     * 
     * Displays the total value of comics in the publisher, along with the total
     * number of issues.
     * 
     * Displays a list of the series in the publisher, displaying their name
     * and the number of comics from that series.
     * Allows the user to select a series to view.
     * 
     * Parses the user's input, and directs them to the appropriate menu.
     * 
     * @param publisher The publisher to view.
     */
    private void viewPublisherMenu(Collection publisher) {
        Map<String, ? extends Collection> map = publisher.getChildren();
        ArrayList<Collection> series = new ArrayList<Collection>();
        series.addAll(map.values());

        while (currentUser != null) {
            System.out.println("\n=== " + publisher.getName() + " ===");
            System.out.println("Total value: $" + publisher.getValue());
            System.out.println("Total issues: " + publisher.getComicCount());
            System.out.println("Series:");
            for (int i = 0; i < series.size(); i++) {

                System.out
                        .println((i + 1) + ". " + series.get(i).getName() + " (" + series.get(i).getComicCount() + ")");
            }
            System.out.println("\nPlease enter a number to browse a series, or enter a command:");
            System.out.println(ADD_INPUT + " -> Add a comic to your collection");
            System.out.println(SEARCH_INPUT + " -> Search your collection");
            System.out.println(BACK_INPUT + " -> Back to collection");
            System.out.println(LOGOUT_INPUT + " -> Logout");
            System.out.println(EXIT_INPUT + " -> Exit program");

            String input = System.console().readLine().toLowerCase();
            switch (input) {
                case ADD_INPUT:
                    addComicMenu();
                    break;
                case SEARCH_INPUT:
                    searchCollectionMenu();
                    break;
                case BACK_INPUT:
                    return;
                case LOGOUT_INPUT:
                    currentUser = null;
                    currentCollection = null;
                    mainMenu();
                case EXIT_INPUT:
                    exit();
                    break;
                default:
                    int index = 0;
                    try {
                        index = Integer.parseInt(input);
                    } catch (NumberFormatException e) {
                        System.out.println(BROWSE_INVALID_INPUT_MESSAGE);
                        continue;
                    }
                    if (index < 1 || index > series.size()) {
                        System.out.println(BROWSE_INVALID_INPUT_MESSAGE);
                        continue;
                    }
                    viewSeriesMenu(series.get(index - 1));
                    break;
            }
        }
    }

    /**
     * Displays the series menu.
     * Allows the user to view and select volumes belonging to the given series,
     * add a comic to their collection, go back, logout, or exit.
     * 
     * Displays the total value of comics in the series, along with the total
     * number of issues.
     * 
     * Displays a list of the volumes in the series, displaying their name
     * and the number of comics from that volume.
     * Allows the user to select a volume to view.
     * 
     * Parses the user's input, and directs them to the appropriate menu.
     * 
     * @param series The series to view.
     */
    private void viewSeriesMenu(Collection series) {
        ArrayList<Collection> volumes = new ArrayList<>(series.getChildren().values());
        while (currentUser != null) {
            System.out.println("\n=== " + series.getName() + " ===");
            System.out.println("Total value: $" + series.getValue());
            System.out.println("Total issues: " + series.getComicCount());
            System.out.println("Volumes:");
            for (int i = 0; i < volumes.size(); i++) {
                System.out.println(
                        (i + 1) + ". " + volumes.get(i).getName() + " (" + volumes.get(i).getComicCount() + ")");
            }
            System.out.println("\nPlease enter a number to browse a volume, or enter a command:");
            System.out.println(ADD_INPUT + " -> Add a comic to your collection");
            System.out.println(SEARCH_INPUT + " -> Search your collection");
            System.out.println(BACK_INPUT + " -> Back to publisher");
            System.out.println(LOGOUT_INPUT + " -> Logout");
            System.out.println(EXIT_INPUT + " -> Exit program");

            String input = System.console().readLine().toLowerCase();
            switch (input) {
                case ADD_INPUT:
                    addComicMenu();
                    break;
                case SEARCH_INPUT:
                    searchCollectionMenu();
                    break;
                case BACK_INPUT:
                    return;
                case LOGOUT_INPUT:
                    currentUser = null;
                    currentCollection = null;
                    mainMenu();
                case EXIT_INPUT:
                    exit();
                    break;
                default:
                    int index = 0;
                    try {
                        index = Integer.parseInt(input);
                    } catch (NumberFormatException e) {
                        System.out.println(BROWSE_INVALID_INPUT_MESSAGE);
                        continue;
                    }
                    if (index < 1 || index > volumes.size()) {
                        System.out.println(BROWSE_INVALID_INPUT_MESSAGE);
                        continue;
                    }
                    viewVolumeMenu(volumes.get(index - 1));
                    break;
            }
        }
    }

    /**
     * Displays the volume menu.
     * Allows the user to view and select issues belonging to the given volume,
     * add a comic to their collection, go back, logout, or exit.
     * 
     * Displays the total value of comics in the volume, along with the total
     * number of issues.
     * 
     * Displays a list of the issues in the volume, displaying their name
     * and the number of comics from that issue.
     * Allows the user to select an issue to view.
     * 
     * Parses the user's input, and directs them to the appropriate menu.
     * 
     * @param volume The volume to view.
     */
    private void viewVolumeMenu(Collection volume) {
        ArrayList<Collection> issues = new ArrayList<>(volume.getChildren().values());

        while (currentUser != null) {
            System.out.println("\n=== " + volume.getName() + " ===");
            System.out.println("Total value: $" + volume.getValue());
            System.out.println("Total issues: " + volume.getComicCount());
            System.out.println("Issues:");
            for (int i = 0; i < issues.size(); i++) {
                System.out
                        .println((i + 1) + ". " + issues.get(i).getName() + " (" + issues.get(i).getComicCount() + ")");
            }
            System.out.println("\nPlease enter a number to browse an issue, or enter a command:");
            System.out.println(ADD_INPUT + " -> Add a comic to your collection");
            System.out.println(SEARCH_INPUT + " -> Search your collection");
            System.out.println(BACK_INPUT + " -> Back to series");
            System.out.println(LOGOUT_INPUT + " -> Logout");
            System.out.println(EXIT_INPUT + " -> Exit program");

            String input = System.console().readLine().toLowerCase();
            switch (input) {
                case ADD_INPUT:
                    addComicMenu();
                    break;
                case SEARCH_INPUT:
                    searchCollectionMenu();
                    break;
                case BACK_INPUT:
                    return;
                case LOGOUT_INPUT:
                    currentUser = null;
                    currentCollection = null;
                    mainMenu();
                case EXIT_INPUT:
                    exit();
                    break;
                default:
                    int index = 0;
                    try {
                        index = Integer.parseInt(input);
                    } catch (NumberFormatException e) {
                        System.out.println(BROWSE_INVALID_INPUT_MESSAGE);
                        continue;
                    }
                    if (index < 1 || index > issues.size()) {
                        System.out.println(BROWSE_INVALID_INPUT_MESSAGE);
                        continue;
                    }
                    viewIssueMenu(issues.get(index - 1));
                    break;
            }
        }
    }

    /**
     * Displays the issue menu.
     * Allows the user to view and select comics belonging to the given issue,
     * add a comic to their collection, go back, logout, or exit.
     * 
     * Displays the total value of comics of the issue, along with the total
     * number of issues.
     * 
     * Displays a list of the comics in the issue, displaying their name, grade (if
     * graded), slabbed (if slabbed), and value.
     * 
     * Allows the user to select a comic to view.
     * 
     * Parses the user's input, and directs them to the appropriate menu.
     * 
     * @param issue The issue to view.
     */
    private void viewIssueMenu(Collection issue) {
        ArrayList<Comic> comics = issue.getComics();

        while (currentCollection != null) {
            System.out.println("\n=== " + issue.getName() + " ===");
            System.out.println("Total value: $" + issue.getValue());
            System.out.println("Total comics: " + issue.getComicCount());
            System.out.println("Comics:");

            for (int i = 0; i < comics.size(); i++) {
                Comic comic = comics.get(i);
                System.out.println(
                        (i + 1) + ". " + comic.getComicTitle() + " ($" + comic.getValue() + ")");
            }

            System.out.println("\nPlease enter a number to view a comic, or enter a command:");
            System.out.println(ADD_INPUT + " -> Add a comic to your collection");
            System.out.println(SEARCH_INPUT + " -> Search your collection");
            System.out.println(BACK_INPUT + " -> Back to volume");
            System.out.println(LOGOUT_INPUT + " -> Logout");
            System.out.println(EXIT_INPUT + " -> Exit program");

            String input = System.console().readLine().toLowerCase();
            switch (input) {
                case ADD_INPUT:
                    addComicMenu();
                    break;
                case SEARCH_INPUT:
                    searchCollectionMenu();
                    break;
                case BACK_INPUT:
                    return;
                case LOGOUT_INPUT:
                    currentUser = null;
                    currentCollection = null;
                    mainMenu();
                case EXIT_INPUT:
                    exit();
                    break;
                default:
                    int index = 0;
                    try {
                        index = Integer.parseInt(input);
                    } catch (NumberFormatException e) {
                        System.out.println(BROWSE_INVALID_INPUT_MESSAGE);
                        continue;
                    }
                    if (index < 1 || index > comics.size()) {
                        System.out.println(BROWSE_INVALID_INPUT_MESSAGE);
                        continue;
                    }
                    viewComicMenu(comics.get(index - 1));
                    break;
            }
        }
    }

    /**
     * Displays the comic menu.
     * Allows the user to view the details of the given comic, edit the comic,
     * remove the comic, add a comic to their collection, go back, logout, or exit.
     * 
     * Displays the details of the comic. * indicates that the attribute is optional
     * (may be empty/blank):
     * Publisher, e.g. "Marvel", "Image", etc.
     * Series Title, e.g. "The Magnificent Ms. Marvel"
     * Volume Number
     * Issue Number
     * Publication Date
     * Creators*
     * Principle Characters*
     * Description*
     * Value ($$)*
     * 
     * Parses the user's input, creates the appropriate action, and executes it.
     * The result of the action is used for feedback and to determine the next menu
     * to display.
     * 
     * @param comic The comic to view.
     */
    private void viewComicMenu(Comic comic) {
        while (currentUser != null) {
            System.out.println("\n=== " + comic.getComicTitle() + " ===");
            System.out.println("Publisher: " + comic.getPublisher());
            System.out.println("Series Title: " + comic.getSeries());
            System.out.println("Volume Number: " + comic.getVolume());
            System.out.println("Issue Number: " + comic.getIssue());
            System.out.println("Story Title: " + comic.getComicTitle());
            System.out.println("Publication Date: " + comic.getPublicationDate());
            System.out.println("Creators: " + comic.getCreators());
            System.out.println("Principle Characters: " + comic.getPrincipleCharacters());
            System.out.println("Description: " + comic.getDescription());
            System.out.println("Value: $" + comic.getValue());
            if (comic instanceof GradeDecorator<?>) {
                GradeDecorator<Comic> gradedComic = (GradeDecorator<Comic>) comic;
                System.out.println("Grade: " + gradedComic.getGrade());
            }
            if (comic instanceof SlabDecorator<?>) {
                SlabDecorator<Comic> slabbedComic = (SlabDecorator<Comic>) comic;

                Comic gradedComic = slabbedComic;
                while (!(gradedComic instanceof GradeDecorator)) {
                    gradedComic = gradedComic.getComic();
                }
                System.out.println("Grade: " + ((GradeDecorator<Comic>) gradedComic).getGrade());

                System.out.println("Slabbed: " + slabbedComic.getSlabbed());
            }
            System.out.println("\nPlease enter a command:");
            System.out.println(EDIT_INPUT + " -> Edit comic");
            System.out.println(REMOVE_INPUT + " -> Remove comic from your collection");
            System.out.println(ADD_INPUT + " -> Add a comic to your collection");
            System.out.println(SEARCH_INPUT + " -> Search your collection");
            System.out.println(BACK_INPUT + " -> Back to issue");
            System.out.println(LOGOUT_INPUT + " -> Logout");
            System.out.println(EXIT_INPUT + " -> Exit program");

            String input = System.console().readLine().toLowerCase();
            switch (input) {
                case EDIT_INPUT:
                    editComicMenu(comic);
                    break;
                case REMOVE_INPUT:
                    removeComicMenu(comic);
                    break;
                case ADD_INPUT:
                    addComicMenu();
                    break;
                case SEARCH_INPUT:
                    searchCollectionMenu();
                    break;
                case BACK_INPUT:
                    return;
                case LOGOUT_INPUT:
                    currentUser = null;
                    currentCollection = null;
                    mainMenu();
                case EXIT_INPUT:
                    exit();
                    break;
                default:
                    System.out.println(INVALID_COMMAND_MESSAGE);
                    break;
            }
        }
    }

    /**
     * Allows the user to add a comic to their collection.
     * Gives them the option to add a comic manually or search the database.
     * Parses the user's input, and directs them to the appropriate menu.
     */
    private void addComicMenu() {
        while (currentUser != null) {
            System.out.println("\n=== Add Comic ===");
            System.out.println("Please enter a command:");
            System.out.println(MANUAL_ADD_INPUT + " -> Add a comic manually");
            System.out.println(DATABASE_ADD_INPUT + " -> Search the database");
            System.out.println(BACK_INPUT + " -> Back to collection");
            System.out.println(LOGOUT_INPUT + " -> Logout");
            System.out.println(EXIT_INPUT + " -> Exit program");

            String input = System.console().readLine().toLowerCase();
            switch (input) {
                case MANUAL_ADD_INPUT:
                    addComicManuallyMenu();
                    break;
                case DATABASE_ADD_INPUT:
                    searchDatabaseMenu();
                    break;
                case BACK_INPUT:
                    return;
                case LOGOUT_INPUT:
                    currentUser = null;
                    currentCollection = null;
                    mainMenu();
                case EXIT_INPUT:
                    exit();
                    break;
                default:
                    System.out.println(INVALID_COMMAND_MESSAGE);
                    break;
            }
            viewCollectionMenu();
        }
    }

    /**
     * Allows the user to add a comic manually.
     * Prompts the user for the details of the comic.
     * Parses the user's input, creates the appropriate action, and executes it.
     * 
     * If the user enters details regarding a slab or grade, the comic is added to
     * the user's collection first, then separate grade and slab actions are
     * created and executed.
     * 
     * The result of the action is used for feedback and to determine the next menu
     * to display.
     */
    private void addComicManuallyMenu() {
        while (currentUser != null) {
            System.out.println("\n=== Add Comic Manually ===");
            System.out.println(BACK_MESSAGE);
            System.out.println("Please enter the comic's details:");
            System.out.println("Publisher:");
            String publisher = System.console().readLine();
            if (publisher.equals(BACK_INPUT)) {
                return;
            }
            System.out.println("Series:");
            String series = System.console().readLine();
            if (series.equals(BACK_INPUT)) {
                return;
            }
            System.out.println("Volume:");
            String volume = System.console().readLine();
            if (volume.equals(BACK_INPUT)) {
                return;
            }
            System.out.println("Issue:");
            String issue = System.console().readLine();
            if (issue.equals(BACK_INPUT)) {
                return;
            }
            System.out.println("Publication Date:");
            String publicationDate = System.console().readLine();
            if (publicationDate.equals(BACK_INPUT)) {
                return;
            }
            System.out.println("Title:");
            String comicTitle = System.console().readLine();
            if (comicTitle.equals(BACK_INPUT)) {
                return;
            }
            System.out.println("Creators (separate by commas):");
            String creators = System.console().readLine();
            if (creators.equals(BACK_INPUT)) {
                return;
            }
            System.out.println("Principle Characters (separate by commas):");
            String principleCharacters = System.console().readLine();
            if (principleCharacters.equals(BACK_INPUT)) {
                return;
            }
            System.out.println("Description:");
            String description = System.console().readLine();
            if (description.equals(BACK_INPUT)) {
                return;
            }

            Double value = 0.0;
            while (currentUser != null) {
                System.out.println("Value:");
                String valueString = System.console().readLine();
                if (valueString.equals(BACK_INPUT)) {
                    return;
                }
                try {
                    value = Double.parseDouble(valueString);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid value. Please enter a number.");
                    continue;
                }
            }

            // parse creators and principle characters
            ArrayList<String> creatorsList = new ArrayList<String>();
            for (String creator : creators.split(",")) {
                creatorsList.add(creator.trim());
            }
            ArrayList<String> principleCharactersList = new ArrayList<String>();
            for (String character : principleCharacters.split(",")) {
                principleCharactersList.add(character.trim());
            }

            // create the comic and execute the add action
            Comic comic = new ConcreteComic(publisher, series, volume, issue, publicationDate, comicTitle, creatorsList,
                    principleCharactersList, description, value);
            AddComicAction addComicAction = new AddComicAction(currentUser.getUsername(), comic, pcDao);
            ActionResult<Comic> result = addComicAction.execute();
            System.out.println(result.getMessage());

            // ask if the comic is graded
            boolean graded = false;
            while (currentUser != null) {
                System.out.println("Is the comic graded? (y/N)");
                String input = System.console().readLine().toLowerCase();
                if (input.equals("y")) {
                    graded = true;
                    break;
                } else if (input.equals("n") || input.equals("")) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter 'y' or 'n'.");
                }
            }

            // if the comic is graded, create and execute the grade action on the previously
            // added comic
            if (graded) {
                int grade = 0;
                while (currentUser != null) {
                    System.out.println("Please enter the grade grade:");
                    String gradeString = System.console().readLine();
                    if (gradeString.equals(BACK_INPUT)) {
                        return;
                    }
                    try {
                        grade = Integer.parseInt(gradeString);
                        // validate the grade between 1 and 10
                        if (grade < 1 || grade > 10) {
                            System.out.println("Invalid grade. Please enter a number between 1 and 10.");
                            continue;
                        }
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid grade. Please enter a number.");
                        continue;
                    }
                }
                GradeComicAction gradeComicAction = new GradeComicAction(currentUser.getUsername(), comic, grade,
                        pcDao);
                result = gradeComicAction.execute();
                comic = result.getResult(); // update the comic to the graded comic
                System.out.println(result.getMessage());
            }

            // ask if the comic is slabbed
            boolean slabbed = false;
            while (currentUser != null) {
                System.out.println("Is the comic slabbed? (y/N)");
                String input = System.console().readLine().toLowerCase();
                if (input.equals("y")) {
                    slabbed = true;
                    break;
                } else if (input.equals("n") || input.equals("")) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter 'y' or 'n'.");
                }
            }

            // if the comic is slabbed, create and execute the slab action on the graded
            // comic
            if (slabbed) {
                SlabComicAction slabComicAction = new SlabComicAction(currentUser.getUsername(), comic, true, pcDao);
                result = slabComicAction.execute();
                System.out.println(result.getMessage());
            }
            viewCollectionMenu();
        }
    }

    /**
     * Allows the user to edit the given comic.
     * Prompts the user for which attribute to edit, and the new value.
     * 
     * Directs them to the edit menu for the given attribute.
     * 
     * @param comic The comic to edit.
     */
    private void editComicMenu(Comic comic) {
        while (currentUser != null) {
            System.out.println("\n=== Edit Comic ===");
            System.out.println("Please enter a command:");
            System.out.println(EDIT_PUBLISHER_INPUT + " -> Edit publisher");
            System.out.println(EDIT_SERIES_INPUT + " -> Edit series");
            System.out.println(EDIT_VOLUME_INPUT + " -> Edit volume");
            System.out.println(EDIT_ISSUE_INPUT + " -> Edit issue");
            System.out.println(EDIT_PUBLICATION_DATE_INPUT + " -> Edit publication date");
            System.out.println(EDIT_TITLE_INPUT + " -> Edit title");
            System.out.println(EDIT_CREATORS_INPUT + " -> Edit creators");
            System.out.println(EDIT_PRINCIPLE_CHARACTERS_INPUT + " -> Edit principle characters");
            System.out.println(EDIT_DESCRIPTION_INPUT + " -> Edit description");
            System.out.println(EDIT_VALUE_INPUT + " -> Edit value");
            System.out.println(EDIT_GRADE_INPUT + " -> Edit grade");
            System.out.println(EDIT_SLAB_INPUT + " -> Edit slabbed");
            System.out.println(BACK_INPUT + " -> Back to comic");
            System.out.println(LOGOUT_INPUT + " -> Logout");
            System.out.println(EXIT_INPUT + " -> Exit program");

            String input = System.console().readLine().toLowerCase();
            switch (input) {
                case EDIT_PUBLISHER_INPUT:
                    editComicAttributeMenu(comic, ComicAttribute.PUBLISHER);
                    break;
                case EDIT_SERIES_INPUT:
                    editComicAttributeMenu(comic, ComicAttribute.SERIES);
                    break;
                case EDIT_VOLUME_INPUT:
                    editComicAttributeMenu(comic, ComicAttribute.VOLUME);
                    break;
                case EDIT_ISSUE_INPUT:
                    editComicAttributeMenu(comic, ComicAttribute.ISSUE);
                    break;
                case EDIT_PUBLICATION_DATE_INPUT:
                    editComicAttributeMenu(comic, ComicAttribute.PUBLICATION_DATE);
                    break;
                case EDIT_TITLE_INPUT:
                    editComicAttributeMenu(comic, ComicAttribute.TITLE);
                    break;
                case EDIT_CREATORS_INPUT:
                    editComicAttributeMenu(comic, ComicAttribute.CREATORS);
                    break;
                case EDIT_PRINCIPLE_CHARACTERS_INPUT:
                    editComicAttributeMenu(comic, ComicAttribute.PRINCIPLE_CHARACTERS);
                    break;
                case EDIT_DESCRIPTION_INPUT:
                    editComicAttributeMenu(comic, ComicAttribute.DESCRIPTION);
                    break;
                case EDIT_VALUE_INPUT:
                    editComicAttributeMenu(comic, ComicAttribute.VALUE);
                    break;
                case EDIT_GRADE_INPUT:
                    editComicAttributeMenu(comic, ComicAttribute.GRADE);
                    break;
                case EDIT_SLAB_INPUT:
                    editComicAttributeMenu(comic, ComicAttribute.SLABBED);
                    break;
                case BACK_INPUT:
                    return;
                case LOGOUT_INPUT:
                    currentUser = null;
                    currentCollection = null;
                    mainMenu();
                case EXIT_INPUT:
                    exit();
                    break;
                default:
                    System.out.println(INVALID_COMMAND_MESSAGE);
                    break;
            }
            viewCollectionMenu();
        }
    }

    /**
     * Allows the user to edit the given attribute of the given comic.
     * Prompts the user for the new value of the attribute.
     * 
     * Parses the user's input, creates the appropriate action, and executes it.
     * The result of the action is used for feedback and to determine the next menu
     * to display.
     * 
     * @param comic     The comic to edit.
     * @param attribute The attribute to edit.
     */
    private void editComicAttributeMenu(Comic comic, ComicAttribute attribute) {
        while (currentUser != null) {
            System.out.println("\n=== Edit " + attribute.toString() + " ===");
            System.out.println(BACK_MESSAGE);
            System.out.println("Please enter the new value:");
            String input = System.console().readLine();
            if (input.equals(BACK_INPUT)) {
                return;
            }

            // create a deep copy of the comic
            Comic newComic = comic.copy();
            switch (attribute) {
                case PUBLISHER:
                    newComic.setPublisher(input);
                    ActionResult<Comic> result1 = new EditComicAction(currentUser.getUsername(), comic, newComic,
                            pcDao).execute();
                    System.out.println(result1.getMessage());
                    break;
                case SERIES:
                    newComic.setSeries(input);
                    ActionResult<Comic> result2 = new EditComicAction(currentUser.getUsername(), comic, newComic,
                            pcDao).execute();
                    System.out.println(result2.getMessage());
                    break;
                case VOLUME:
                    newComic.setVolume(input);
                    ActionResult<Comic> result3 = new EditComicAction(currentUser.getUsername(), comic, newComic,
                            pcDao).execute();
                    System.out.println(result3.getMessage());
                    break;
                case ISSUE:
                    newComic.setIssue(input);
                    ActionResult<Comic> result4 = new EditComicAction(currentUser.getUsername(), comic, newComic,
                            pcDao).execute();
                    System.out.println(result4.getMessage());
                    break;
                case PUBLICATION_DATE:
                    newComic.setPublicationDate(input);
                    ActionResult<Comic> result5 = new EditComicAction(currentUser.getUsername(), comic, newComic,
                            pcDao).execute();
                    System.out.println(result5.getMessage());
                    break;
                case TITLE:
                    newComic.setComicTitle(input);
                    ActionResult<Comic> result6 = new EditComicAction(currentUser.getUsername(), comic, newComic,
                            pcDao).execute();
                    System.out.println(result6.getMessage());
                    break;
                case CREATORS:
                    ArrayList<String> creators = new ArrayList<String>();
                    for (String creator : input.split(",")) {
                        creators.add(creator.trim());
                    }
                    newComic.setCreators(creators);
                    ActionResult<Comic> result7 = new EditComicAction(currentUser.getUsername(), comic, newComic,
                            pcDao).execute();
                    System.out.println(result7.getMessage());
                    break;
                case PRINCIPLE_CHARACTERS:
                    ArrayList<String> principleCharacters = new ArrayList<String>();
                    for (String character : input.split(",")) {
                        principleCharacters.add(character.trim());
                    }
                    newComic.setPrincipleCharacters(principleCharacters);
                    ActionResult<Comic> result8 = new EditComicAction(currentUser.getUsername(), comic, newComic,
                            pcDao).execute();
                    System.out.println(result8.getMessage());
                    break;
                case DESCRIPTION:
                    newComic.setDescription(input);
                    ActionResult<Comic> result9 = new EditComicAction(currentUser.getUsername(), comic, newComic,
                            pcDao).execute();
                    System.out.println(result9.getMessage());
                    break;
                case VALUE:
                    Double value = 0.0;
                    try {
                        value = Double.parseDouble(input);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid value. Please enter a number.");
                        continue;
                    }
                    newComic.setValue(value);
                    EditComicAction editComicAction = new EditComicAction(currentUser.getUsername(), comic, newComic,
                            pcDao);
                    ActionResult<Comic> result = editComicAction.execute();
                    System.out.println(result.getMessage());
                    break;
                case GRADE:
                    int grade = 0;
                    try {
                        grade = Integer.parseInt(input);
                        // valaidate that the grade is between 1 and 10
                        if (grade < 1 || grade > 10) {
                            System.out.println("Invalid grade. Please enter a number between 1 and 10.");
                            continue;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid grade. Please enter a number.");
                        continue;
                    }
                    // create and execute the grade action
                    GradeComicAction gradeComicAction = new GradeComicAction(currentUser.getUsername(), newComic, grade,
                            pcDao);
                    result = gradeComicAction.execute();
                    System.out.println(result.getMessage());
                    break;
                case SLABBED:
                    System.out.println("\nIs the comic slabbed? (y/N)");
                    boolean slabbed = false;
                    if (input.equals("y")) {
                        slabbed = true;
                    } else if (input.equals("n") || input.equals("")) {
                        slabbed = false;
                    } else {
                        System.out.println("Invalid input. Please enter 'y' or 'n'.");
                        continue;
                    }
                    if (slabbed) {
                        // create and execute the slab action
                        SlabComicAction slabComicAction = new SlabComicAction(currentUser.getUsername(), newComic, true,
                                pcDao);
                        result = slabComicAction.execute();
                        System.out.println(result.getMessage());
                    } else {
                        // here we would create and execute an an 'unslab' action, if one existed
                        System.out.println("You don't own the unslabbinng DLC. Please purchase it for $19.99.");
                    }
                    break;
            }
            viewCollectionMenu();
        }
    }

    /**
     * Allows the user to remove a comic from their collection.
     * Parses the user's input, creates the appropriate action, and executes it.
     * The result of the action is used for feedback and to determine the next menu
     * to display.
     * 
     * @param comic The comic to remove.
     */
    private void removeComicMenu(Comic comic) {
        while (currentUser != null) {
            System.out.println("\n=== Remove Comic ===");
            System.out.println("Are you sure you want to remove " + comic.getComicTitle() + "? (y/N)");

            String input = System.console().readLine().toLowerCase();
            if (input.equals("y")) {
                RemoveComicAction removeComicAction = new RemoveComicAction(currentUser.getUsername(), comic, pcDao);
                ActionResult<Comic> result = removeComicAction.execute();
                System.out.println(result.getMessage());
                if (result.getSuccess()) {
                    viewCollectionMenu();
                }
            } else if (input.equals("n") || input.equals("")) {
                return;
            } else {
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
            }
        }
    }

    /**
     * Allows the user to search the database.
     * Prompts the user for the search parameters.
     * 
     * Parses the user's input, creates the appropriate action, and executes it.
     * The result of the action is used for feedback and to determine the next menu
     * to display.
     */
    private void searchDatabaseMenu() {
        while (currentUser != null) {
            System.out.println("\nWould you like to search by exact match? (y/N)");
            String exactMatchDecision = System.console().readLine().toLowerCase();
            if (exactMatchDecision.equals(BACK_INPUT))
                return;

            // parse the user's input into a boolean
            boolean exactMatch = false;
            if (exactMatchDecision.equals("y")) {
                exactMatch = true;
            } else if (exactMatchDecision.equals("n") || exactMatchDecision.equals("")) {
                exactMatch = false;
            } else {
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
                continue;
            }

            // prompt for the sort strategy (either default or publication date)
            System.out.println("\nWould you like to sort the results by publication date? (y/N)");
            // parse the user's input into a boolean
            SortType sortType = SortType.DEFAULT;
            String sortDecision = System.console().readLine().toLowerCase();

            if (sortDecision.equals("y")) {
                sortType = SortType.PUBLICATION_DATE;
            } else if (sortDecision.equals("n") || sortDecision.equals("")) {
                //
            } else {
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
                continue;
            }

            System.out.println("\nWhat parameter would you like to search by?");
            System.out.println(SEARCH_SERIES_INPUT + " -> Search By Series");
            System.out.println(SEARCH_ISSUE_INPUT + " -> Search By Issue Number");
            System.out.println(SEARCH_STORY_TITLE_INPUT + " -> Search By Story Title");
            System.out.println(SEARCH_PUBLISHER_INPUT + " -> Search By Publisher");
            System.out.println(SEARCH_PUBLICATION_DATE_INPUT + " -> Search By Publication Date");
            System.out.println(SEARCH_CREATORS_INPUT + " -> Search By Creators");
            System.out.println(BACK_MESSAGE + " -> Back to the previous menu");
            System.out.println("Please enter your selection: ");

            String searchDecision = System.console().readLine().toLowerCase();
            if (searchDecision.equals(BACK_INPUT))
                return;

            ActionResult<ArrayList<Comic>> result = null;
            switch (searchDecision) {
                case SEARCH_SERIES_INPUT:
                    System.out.println("\nPlease type series title: ");
                    String query = System.console().readLine();
                    if (query.equals(BACK_INPUT))
                        return;

                    if (query == null || query.equals("")) {
                        System.out.println("Invalid input. Please enter a series title.");
                        continue;
                    }

                    SearchDatabaseAction searchAction = new SearchDatabaseAction(sortType, SearchField.SERIES_TITLE,
                            exactMatch, query, dbDao);
                    result = searchAction.execute();
                    break;
                case SEARCH_ISSUE_INPUT:
                    System.out.println("\nPlease type issue number: ");
                    query = System.console().readLine();
                    if (query.equals(BACK_INPUT))
                        return;

                    if (query == null || query.equals("")) {
                        System.out.println("Invalid input. Please enter an issue name.");
                        continue;
                    }

                    searchAction = new SearchDatabaseAction(sortType, SearchField.ISSUE_NUMBER, exactMatch, query,
                            dbDao);
                    result = searchAction.execute();
                    break;
                case SEARCH_STORY_TITLE_INPUT:
                    System.out.println("\nPlease type story title: ");
                    query = System.console().readLine();
                    if (query.equals(BACK_INPUT))
                        return;

                    if (query == null || query.equals("")) {
                        System.out.println("Invalid input. Please enter a story title.");
                        continue;
                    }

                    searchAction = new SearchDatabaseAction(sortType, SearchField.STORY_TITLE, exactMatch, query,
                            dbDao);
                    result = searchAction.execute();
                    break;
                case SEARCH_PUBLISHER_INPUT:
                    System.out.println("\nPlease type publisher: ");
                    query = System.console().readLine();
                    if (query.equals(BACK_INPUT))
                        return;

                    if (query == null || query.equals("")) {
                        System.out.println("Invalid input. Please enter a publisher name.");
                        continue;
                    }

                    searchAction = new SearchDatabaseAction(sortType, SearchField.PUBLISHER, exactMatch, query, dbDao);
                    result = searchAction.execute();
                    break;
                case SEARCH_PUBLICATION_DATE_INPUT:
                    System.out.println("\nPlease type publication date: ");
                    query = System.console().readLine();
                    if (query.equals(BACK_INPUT))
                        return;

                    if (query == null || query.equals("")) {
                        System.out.println("Invalid input. Please enter a publication date.");
                        continue;
                    }

                    searchAction = new SearchDatabaseAction(sortType, SearchField.PUBLICATION_DATE, exactMatch, query,
                            dbDao);
                    result = searchAction.execute();
                    break;
                case SEARCH_CREATORS_INPUT:
                    System.out.println("\nPlease type creator name: ");
                    query = System.console().readLine();
                    if (query.equals(BACK_INPUT))
                        return;

                    if (query == null || query.equals("")) {
                        System.out.println("Invalid input. Please enter a creator name.");
                        continue;
                    }

                    searchAction = new SearchDatabaseAction(sortType, SearchField.CREATOR_NAMES, exactMatch, query,
                            dbDao);
                    result = searchAction.execute();
                    break;
                default:
                    System.out.println(INVALID_COMMAND_MESSAGE);
                    break;
            }
            if (result == null)
                return;

            System.out.println(result.getMessage());
            ArrayList<Comic> comics = result.getResult();
            if (result.getSuccess()) {
                for (int i = 0; i < comics.size(); i++) {
                    System.out.println((i + 1) + ". " + comics.get(i).getComicTitle());
                }
                System.out.println("\nPlease enter a number to view a comic, or enter a command:");
                System.out.println(BACK_INPUT + " -> Back to collection");
                System.out.println(LOGOUT_INPUT + " -> Logout");
                System.out.println(EXIT_INPUT + " -> Exit program");

                String input = System.console().readLine().toLowerCase();
                switch (input) {
                    case BACK_INPUT:
                        return;
                    case LOGOUT_INPUT:
                        currentUser = null;
                        currentCollection = null;
                        mainMenu();
                    case EXIT_INPUT:
                        exit();
                        break;
                    default:
                        int index = 0;
                        try {
                            index = Integer.parseInt(input);
                        } catch (NumberFormatException e) {
                            System.out.println(BROWSE_INVALID_INPUT_MESSAGE);
                            continue;
                        }
                        if (index < 1 || index > comics.size()) {
                            System.out.println(BROWSE_INVALID_INPUT_MESSAGE);
                            continue;
                        }
                        viewDatabaseComicMenu(comics.get(index - 1));
                        break;
                }
            }
        }

    }

    private void viewDatabaseComicMenu(Comic comic) {
        while (currentUser != null) {
            System.out.println("\n=== " + comic.getComicTitle() + " ===");
            System.out.println("Publisher: " + comic.getPublisher());
            System.out.println("Series Title: " + comic.getSeries());
            System.out.println("Volume Number: " + comic.getVolume());
            System.out.println("Issue Number: " + comic.getIssue());
            System.out.println("Story Title: " + comic.getComicTitle());
            System.out.println("Publication Date: " + comic.getPublicationDate());
            System.out.println("Creators: " + comic.getCreators());
            System.out.println("Principle Characters: " + comic.getPrincipleCharacters());
            System.out.println("Description: " + comic.getDescription());

            System.out.println("\nPlease enter a command:");
            System.out.println(ADD_INPUT + " -> Add the comic to your collection");
            System.out.println(BACK_INPUT + " -> Back to search results");
            System.out.println(LOGOUT_INPUT + " -> Logout");
            System.out.println(EXIT_INPUT + " -> Exit program");

            String input = System.console().readLine().toLowerCase();
            switch (input) {
                case ADD_INPUT:
                    AddComicAction addComicAction = new AddComicAction(currentUser.getUsername(), comic, pcDao);
                    ActionResult<Comic> result = addComicAction.execute();
                    System.out.println(result.getMessage());
                    viewCollectionMenu();
                    break;
                case BACK_INPUT:
                    return;
                case LOGOUT_INPUT:
                    currentUser = null;
                    currentCollection = null;
                    mainMenu();
                    break;
                case EXIT_INPUT:
                    exit();
                    break;
                default:
                    System.out.println(INVALID_COMMAND_MESSAGE);
                    break;
            }
        }
    }

    /**
     * Allows the user to search their collection.
     * Prompts the user for the search parameters.
     * 
     * Parses the user's input, creates the appropriate action, and executes it.
     * The result of the action is used for feedback and to determine the next menu
     * to display.
     */
    private void searchCollectionMenu() {
        while (currentUser != null) {
            System.out.println("\nWould you like to search by exact match? (y/N)");
            String exactMatchDecision = System.console().readLine().toLowerCase();
            if (exactMatchDecision.equals(BACK_INPUT))
                return;

            // parse the user's input into a boolean
            boolean exactMatch = false;
            if (exactMatchDecision.equals("y")) {
                exactMatch = true;
            } else if (exactMatchDecision.equals("n") || exactMatchDecision.equals("")) {
                exactMatch = false;
            } else {
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
                continue;
            }

            // prompt for the sort strategy (either default or publication date)
            System.out.println("\nWould you like to sort the results by publication date? (y/N)");
            // parse the user's input into a boolean
            SortType sortType = SortType.DEFAULT;
            String sortDecision = System.console().readLine().toLowerCase();

            if (sortDecision.equals("y")) {
                sortType = SortType.PUBLICATION_DATE;
            } else if (sortDecision.equals("n") || sortDecision.equals("")) {
                //
            } else {
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
                continue;
            }

            System.out.println("\nWhat parameter would you like to search by?");
            System.out.println(SEARCH_CREATORS_INPUT + " -> Search By Creators");
            System.out.println(SEARCH_SERIES_INPUT + " -> Search By Series Title");
            System.out.println(SEARCH_DESCRIPTION_INPUT + " -> Search By Description");
            System.out.println(SEARCH_CHARACTERS_INPUT + " -> Search By Characters");
            System.out.println(BACK_MESSAGE + " -> Back to the previous menu");
            System.out.println("Please enter your selection: ");

            String searchDecision = System.console().readLine().toLowerCase();
            if (searchDecision.equals(BACK_INPUT))
                return;

            ActionResult<ArrayList<Comic>> result = null;
            switch (searchDecision) {
                case SEARCH_CREATORS_INPUT:
                    System.out.println("\nPlease type creator name: ");
                    String query = System.console().readLine();
                    if (query.equals(BACK_INPUT))
                        return;

                    if (query == null || query.equals("")) {
                        System.out.println("Invalid input. Please enter creators (comma separated).");
                        continue;
                    }

                    SearchCollectionAction searchAction = new SearchCollectionAction(currentUser.getUsername(),
                            sortType, SearchField.CREATOR_NAMES, exactMatch, query, pcDao);
                    result = searchAction.execute();
                    break;
                case SEARCH_SERIES_INPUT:
                    System.out.println("\nPlease type series title: ");
                    query = System.console().readLine();
                    if (query.equals(BACK_INPUT))
                        return;

                    if (query == null || query.equals("")) {
                        System.out.println("Invalid input. Please enter a series title.");
                        continue;
                    }

                    searchAction = new SearchCollectionAction(currentUser.getUsername(), sortType,
                            SearchField.SERIES_TITLE,
                            exactMatch, query, pcDao);
                    result = searchAction.execute();
                    break;
                case SEARCH_DESCRIPTION_INPUT:
                    System.out.println("\nPlease enter description: ");
                    query = System.console().readLine();
                    if (query.equals(BACK_INPUT))
                        return;

                    if (query == null || query.equals("")) {
                        System.out.println("Invalid input. Please enter a comic description.");
                        continue;
                    }

                    searchAction = new SearchCollectionAction(currentUser.getUsername(), sortType,
                            SearchField.DESCRIPTION,
                            exactMatch, query, pcDao);
                    result = searchAction.execute();
                    break;
                case SEARCH_CHARACTERS_INPUT:
                    System.out.println("\nPlease type character name: ");
                    query = System.console().readLine();
                    if (query.equals(BACK_INPUT))
                        return;

                    if (query == null || query.equals("")) {
                        System.out.println("Invalid input. Please enter the principle characters (comma separated).");
                        continue;
                    }

                    searchAction = new SearchCollectionAction(currentUser.getUsername(), sortType,
                            SearchField.DESCRIPTION,
                            exactMatch, query, pcDao);
                    result = searchAction.execute();
                    break;
                default:
                    System.out.println(INVALID_COMMAND_MESSAGE);
                    break;
            }
            if (result == null)
                return;
            System.out.println(result.getMessage());
            ArrayList<Comic> comics = result.getResult();
            if (result.getSuccess()) {
                for (int i = 0; i < comics.size(); i++) {
                    System.out.println((i + 1) + ". " + comics.get(i).getComicTitle());
                }
                System.out.println("\nPlease enter a number to view a comic, or enter a command:");
                System.out.println(ADD_INPUT + " -> Add a comic to your collection");
                System.out.println(SEARCH_INPUT + " -> Search your collection");
                System.out.println(BACK_INPUT + " -> Back to collection");
                System.out.println(LOGOUT_INPUT + " -> Logout");
                System.out.println(EXIT_INPUT + " -> Exit program");

                String input = System.console().readLine().toLowerCase();
                switch (input) {
                    case ADD_INPUT:
                        addComicMenu();
                        break;
                    case SEARCH_INPUT:
                        searchCollectionMenu();
                        break;
                    case BACK_INPUT:
                        return;
                    case LOGOUT_INPUT:
                        currentUser = null;
                        currentCollection = null;
                        mainMenu();
                    case EXIT_INPUT:
                        exit();
                        break;
                    default:
                        int index = 0;
                        try {
                            index = Integer.parseInt(input);
                        } catch (NumberFormatException e) {
                            System.out.println(BROWSE_INVALID_INPUT_MESSAGE);
                            continue;
                        }
                        if (index < 1 || index > comics.size()) {
                            System.out.println(BROWSE_INVALID_INPUT_MESSAGE);
                            continue;
                        }
                        viewComicMenu(comics.get(index - 1));
                        break;
                }
            }
        }
    }
}
