package com.javarush.task.task32.task3209;

import com.javarush.task.task32.task3209.listeners.FrameListener;
import com.javarush.task.task32.task3209.listeners.TabbedPaneChangeListener;
import com.javarush.task.task32.task3209.listeners.UndoListener;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JFrame implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Новый":
                controller.createNewDocument();
                break;
            case "Открыть":
                controller.openDocument();
                break;
            case "Сохранить":
                controller.saveDocument();
                break;
            case "Сохранить как...":
                controller.saveDocumentAs();
                break;
            case "Выход":
                controller.exit();
                break;
            case "О программе":
                showAbout();
        }
    }
    public View() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            ExceptionHandler.log(e);
        }

    }
    private UndoManager undoManager = new UndoManager();
    private UndoListener undoListener = new UndoListener(undoManager);
    private Controller controller;
    private JTabbedPane tabbedPane = new JTabbedPane();
    private JTextPane htmlTextPane = new JTextPane();
    private JEditorPane plainTextPane = new JEditorPane();

    public Controller getController() {
        return controller;
    }


    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void init(){
        initGui();
        this.addWindowListener(new FrameListener(this));
        this.setVisible(true);
    }

    public void exit(){
        controller.exit();
    }

    public void initMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        MenuHelper.initFileMenu(this,menuBar);
        MenuHelper.initEditMenu(this,menuBar);
        MenuHelper.initStyleMenu(this,menuBar);
        MenuHelper.initAlignMenu(this,menuBar);
        MenuHelper.initColorMenu(this,menuBar);
        MenuHelper.initFontMenu(this,menuBar);
        MenuHelper.initHelpMenu(this,menuBar);

        getContentPane().add(menuBar, BorderLayout.NORTH);
    }

    public void initEditor(){
        htmlTextPane.setContentType("text/html");
        tabbedPane.addTab("HTML", new JScrollPane(htmlTextPane));
        tabbedPane.addTab("Текст", new JScrollPane(plainTextPane));
        tabbedPane.setPreferredSize(new Dimension(100,100));
        getContentPane().add(tabbedPane, BorderLayout.CENTER);
        tabbedPane.addChangeListener(new TabbedPaneChangeListener(this));
    }

    public void initGui(){
        initMenuBar();
        initEditor();
        pack();
    }


    public void selectedTabChanged() {
        switch (tabbedPane.getSelectedIndex()) {
            case 0:
                controller.setPlainText(plainTextPane.getText());
                break;
            case 1:
                plainTextPane.setText(controller.getPlainText());
                break;
        }
        resetUndo();
    }

    public UndoListener getUndoListener() {
        return undoListener;
    }

    public void undo(){
        try {
            undoManager.undo();
        } catch (CannotUndoException e) {
            ExceptionHandler.log(e);
        }
    }

    public void redo(){
        try {
            undoManager.redo();
        } catch (CannotRedoException e) {
            ExceptionHandler.log(e);
        }
    }

    public boolean canUndo(){
        return undoManager.canUndo();
    }

    public boolean canRedo(){
        return undoManager.canRedo();
    }

    public void resetUndo(){
        undoManager.discardAllEdits();
    }

    public boolean isHtmlTabSelected(){
        return tabbedPane.getSelectedIndex() == 0;
    }

    public void selectHtmlTab(){
        tabbedPane.setSelectedIndex(0);
        resetUndo();
    }

    public void update(){
        htmlTextPane.setDocument(controller.getDocument());
    }

    public void showAbout(){
        JOptionPane.showMessageDialog(getContentPane(), "Hello", "Information", JOptionPane.INFORMATION_MESSAGE);
    }

}
