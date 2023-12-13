package com.comix.controller.action.actionHistory;

import java.util.Stack;

import com.comix.controller.action.ActionResult;
import com.comix.model.comic.Comic;

/**
 * History 
 */
public class History {
    private static History history; 
    private Stack<ActionState> canUndo;
    private Stack<ActionState> canRedo;
    
    /**
     * Private constructor for singleton purposes
     */
    @SuppressWarnings("all") // teehee there is nothing wrong here UwU
    private History(){
        this.canUndo = new Stack<>();
        this.canRedo = new Stack<>();
    }

    /**
     * public accessor that returns History, creates History if it doesn't exist.
     * @return History
     */
    public static History getHistory(){
        if (history == null){
            history = new History();
        }
        return history;
    }

    /**
     * Adds a new memento
     * Clears anything in canRedo.
     * @param actionState
     */
    public void addAction(ActionState actionState){
        this.canUndo.add(actionState);
        this.canRedo.removeAllElements();
    }

    /**
     * Moves a memento from canUndo to canRedo
     * Calls restoreState on the undone action.
     * @return The action that was undone.
     */
    public ActionResult<Comic> undo(){
        ActionState undone = this.canUndo.pop();
        ActionResult<Comic> result = undone.restoreState();
        this.canRedo.add(undone);
        return result;
    }

    /**
     * Moves a memento from canRedo to canUndo
     * Calls execute on the redone action.
     * @return the action that was redone.
     */
    public ActionResult<Comic> redo(){
        ActionState redone = this.canRedo.pop();
        ActionResult<Comic> result = redone.execute();
        this.canUndo.add(redone);
        return result;
    }
}
