package com.comix.controller.action;

import com.comix.model.user.User;
import com.comix.persistence.UserDao;

public class CreateAccountAction implements Action<ActionResult<User>> {
    private static final String ACCOUNT_CREATED_SUCCESS_MESSAGE = "Account created successfully!";
    private static final String ACCOUNT_CREATED_FAILURE_MESSAGE = "Error creating account... Please try again.";

    private UserDao userDao;
    private String username;
    private String password;

    /**
     * Constructor for a CreateAccountAction.
     * 
     * @param username The username of the user to be created.
     * @param password The password of the user to be created.
     * @param userDao  The DAO for the user.
     */
    public CreateAccountAction(String username, String password, UserDao userDao) {
        this.username = username;
        this.password = password;
        this.userDao = userDao;
    }

    /**
     * Executes a user's action to create an account.
     * 
     * @return The result of the action, containing the created User.
     */
    public ActionResult<User> execute() {
        try {
            User user = userDao.createAccount(username, password);
            if (user == null) {
                return new ActionResult<User>(false, null, ACCOUNT_CREATED_FAILURE_MESSAGE);
            }
            return new ActionResult<User>(true, user, ACCOUNT_CREATED_SUCCESS_MESSAGE);
        } catch (Exception e) {
            // log error
            e.printStackTrace();
            return new ActionResult<User>(false, null, ACCOUNT_CREATED_FAILURE_MESSAGE);
        }
    }
}
