package com.comix.controller.action.actionHistory;

import com.comix.controller.action.ActionResult;
import com.comix.model.comic.Comic;

public interface UndoableAction {
    public void createMemento();
    public ActionResult<Comic> execute();
    public ActionResult<Comic> restoreState();
}
