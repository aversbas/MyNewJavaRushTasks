package com.javarush.task.task32.task3209.actions;

import com.javarush.task.task32.task3209.View;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class UndoAction extends AbstractAction {
    @Override
    public void actionPerformed(ActionEvent e) {
        view.undo();
    }
        private View view;

    public UndoAction(View view){
        this.view = view;
    }


}
