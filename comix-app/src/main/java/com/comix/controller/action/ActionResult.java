package com.comix.controller.action;

public class ActionResult<T> {
    private boolean success;
    private T result;
    private String message;

    /**
     * Constructor for an ActionResult.
     * 
     * @param success Whether or not the action was successful.
     * @param result  The result of the action.
     * @param message A message to be displayed to the user.
     */
    public ActionResult(boolean success, T result, String message) {
        this.success = success;
        this.result = result;
        this.message = message;
    }

    /**
     * Gets whether or not the action was successful.
     * 
     * @return Whether or not the action was successful.
     */
    public boolean getSuccess() {
        return success;
    }

    /**
     * Gets the result of the action.
     * 
     * @return The result of the action.
     */
    public T getResult() {
        return result;
    }

    /**
     * Gets the message to be displayed to the user.
     * 
     * @return The message to be displayed to the user.
     */
    public String getMessage() {
        return message;
    }
}
