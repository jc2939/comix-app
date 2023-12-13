package com.comix.controller.action;

public interface Action<T> {
    /**
     * Executes a user's action.
     * 
     * @return The result of the action.
     */
    public T execute();
}
