package com.comix.controller.action.actionHistory;

import com.comix.controller.action.ActionResult;
import com.comix.model.comic.Comic;

public class ActionState {
    private UndoableAction originator;

    public ActionState(UndoableAction originator)
    {
        this.originator = originator;
    }

    public ActionResult<Comic> execute()
    {
        return originator.execute();
    }
    
    public ActionResult<Comic> restoreState()
    {
        return originator.restoreState();
    }
}
