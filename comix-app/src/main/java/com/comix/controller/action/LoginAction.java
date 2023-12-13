package com.comix.controller.action;

import com.comix.model.comic.Comic;
import com.comix.model.user.User;
import com.comix.persistence.UserDao;

public class LoginAction implements Action<ActionResult<User>> {
    private static final String ACCOUNT_LOGGED_IN_SUCCESS_MESSAGE = "Login successful!";
    private static final String ACCOUNT_LOGGED_IN_FAILURE_MESSAGE = "Error logging in... Please try again.";

    private UserDao userDao;
    private String username;
    private String password;

    /**
     * Constructor for a LoginAction.
     * 
     * @param username The username of the user to be logged in.
     * @param password The password of the user to be logged in.
     * @param userDao  The DAO for the user.
     */
    public LoginAction(String username, String password, UserDao userDao) {
        this.username = username;
        this.password = password;
        this.userDao = userDao;
    }

    /**
     * Executes a user's action to log in.
     * 
     * @return The result of the action, containing the logged in User.
     */
    public ActionResult<User> execute() {
        try {
            User user = userDao.login(username, password);
            if (user == null) {
                return new ActionResult<User>(false, null, ACCOUNT_LOGGED_IN_FAILURE_MESSAGE);
            }
            return new ActionResult<User>(true, user, ACCOUNT_LOGGED_IN_SUCCESS_MESSAGE);
        } catch (Exception e) {
            return new ActionResult<User>(false, null, ACCOUNT_LOGGED_IN_FAILURE_MESSAGE);
        }
    }
}
