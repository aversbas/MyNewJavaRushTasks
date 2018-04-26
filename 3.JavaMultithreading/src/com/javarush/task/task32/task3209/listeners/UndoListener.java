package com.javarush.task.task32.task3209.listeners;

import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;

public class UndoListener implements UndoableEditListener {
    @Override
    public void undoableEditHappened(UndoableEditEvent e) {
        e.getEdit();
        undoManager.addEdit(e.getEdit());
    }
    private UndoManager undoManager;

    public UndoListener(UndoManager undoManager) {
        this.undoManager = undoManager;
    }
}
