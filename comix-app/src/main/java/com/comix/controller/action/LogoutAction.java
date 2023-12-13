package com.comix.controller.action;

import com.comix.model.comic.Comic;
import com.comix.model.user.User;
import com.comix.persistence.UserDao;

public class LogoutAction implements Action<ActionResult<User>> {
    private static final String ACCOUNT_LOGGED_OUT_SUCCESS_MESSAGE = "Logout successful.";
    private static final String ACCOUNT_LOGGED_OUT_FAILURE_MESSAGE = "Error logging out... Please try again.";

    private UserDao userDao;
    private String username;

    /**
     * Constructor for a LogoutAction.
     * 
     * @param username The integer ID of the user to be logged out.
     * @param userDao  The DAO for the user.
     */
    public LogoutAction(String username, UserDao userDao) {
        this.username = username;
        this.userDao = userDao;
    }

    /**
     * Executes a user's action to log out.
     * 
     * @return The result of the action, containing the logged out User.
     */
    public ActionResult<User> execute() {
        return new ActionResult<User>(true, null, ACCOUNT_LOGGED_OUT_SUCCESS_MESSAGE);
    }
}
