package com.ostsoft.smsplit.display;


import com.ostsoft.smsplit.AutoData;
import com.ostsoft.smsplit.observer.EventType;
import com.ostsoft.smsplit.observer.Observer;
import com.ostsoft.smsplit.util.ResourceUtil;
import com.ostsoft.smsplit.xml.XMLUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class AutoActions implements Observer {
    private final AutoWindow autoWindow;

    private final Action loadAction = new LoadAction();
    private final Action saveAction = new SaveAction();
    private final Action saveAsAction = new SaveAsAction();
    private final Action closeAction = new CloseAction();
    private final Action exitAction = new ExitAction();

    private final Action undoAction = new UndoAction();
    private final Action redoAction = new RedoAction();

    public AutoActions(AutoWindow autoWindow) {
        this.autoWindow = autoWindow;
    }

    public void updateActions() {

    }

    @Override
    public void handleEvent(EventType eventType, String message) {
        if (eventType == EventType.UNDO_REDO_CHANGED) {
            undoAction.setEnabled(autoWindow.getAutoData().getCommandCenter().canUndo());
            redoAction.setEnabled(autoWindow.getAutoData().getCommandCenter().canRedo());
        }
    }

    public Action getLoadAction() {
        return loadAction;
    }

    public Action getSaveAction() {
        return saveAction;
    }

    public Action getSaveAsAction() {
        return saveAsAction;
    }

    public Action getCloseAction() {
        return closeAction;
    }

    public Action getExitAction() {
        return exitAction;
    }

    public Action getUndoAction() {
        return undoAction;
    }

    public Action getRedoAction() {
        return redoAction;
    }


    private class LoadAction extends AbstractAction {
        public LoadAction() {
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
            putValue(NAME, "Load");
            putValue(MNEMONIC_KEY, KeyEvent.VK_L);
            putValue(SHORT_DESCRIPTION, "Load");
            putValue(SMALL_ICON, ResourceUtil.getIcon("images/actions/document-open.png"));
        }

        public void actionPerformed(ActionEvent e) {
            AutoData autoData = autoWindow.getAutoData();
            if (autoData != null) {
                autoData.fireEvent(EventType.LOAD);
            } else {
//                autoData.getEditorFile().load(false);
            }
            autoWindow.getAutoData().fireEvent(EventType.STATUS_BAR_MESSAGE, "Loaded");
            autoData.setConfigChanged(false);
        }
    }

    private class SaveAction extends AbstractAction {

        public SaveAction() {
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
            putValue(NAME, "Save");
            putValue(MNEMONIC_KEY, KeyEvent.VK_S);
            putValue(SHORT_DESCRIPTION, "Save");
            putValue(SMALL_ICON, ResourceUtil.getIcon("images/actions/document-save.png"));
        }

        public void actionPerformed(ActionEvent e) {
            autoWindow.getAutoData().fireEvent(EventType.SAVE);
            autoWindow.getAutoData().setConfigChanged(false);
            XMLUtil.encodeConfig("config.xml", autoWindow.getAutoData().config);
            autoWindow.getAutoData().fireEvent(EventType.STATUS_BAR_MESSAGE, "Saved");
        }

    }

    private class SaveAsAction extends AbstractAction {

        public SaveAsAction() {
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0));
            putValue(NAME, "Save As");
            putValue(MNEMONIC_KEY, KeyEvent.VK_A);
            putValue(SHORT_DESCRIPTION, "Save with new name");
            putValue(SMALL_ICON, ResourceUtil.getIcon("images/actions/document-save-as.png"));
        }

        public void actionPerformed(ActionEvent e) {
            autoWindow.getAutoData().fireEvent(EventType.SAVE_AS);
        }

    }

    private class CloseAction extends AbstractAction {

        public CloseAction() {
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.CTRL_MASK));
            putValue(NAME, "Close file");
            putValue(MNEMONIC_KEY, KeyEvent.VK_C);
            putValue(SHORT_DESCRIPTION, "Close file");
        }

        public void actionPerformed(ActionEvent e) {
//            editor.getAutoData().fireEvent(EventType.CLOSE);
        }

    }

    private class ExitAction extends AbstractAction {

        public ExitAction() {
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
            putValue(NAME, "Exit");
            putValue(MNEMONIC_KEY, KeyEvent.VK_X);
            putValue(SHORT_DESCRIPTION, "Exit the program");
            putValue(SMALL_ICON, ResourceUtil.getIcon("images/actions/system-log-out.png"));
        }

        public void actionPerformed(ActionEvent e) {
//            if (editor.getAutoData() != null) {
//                editor.getAutoData().fireEvent(EventType.EXIT);
//            }
//            else {
            autoWindow.exit();
//            }
        }

    }

    private class UndoAction extends AbstractAction {

        public UndoAction() {
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
            putValue(NAME, "Undo");
            putValue(MNEMONIC_KEY, KeyEvent.VK_U);
            putValue(SHORT_DESCRIPTION, "Undo the last action");
            putValue(SMALL_ICON, ResourceUtil.getIcon("images/actions/edit-undo.png"));
        }

        public void actionPerformed(ActionEvent e) {
            autoWindow.getAutoData().getCommandCenter().undo();
        }

    }

    private class RedoAction extends AbstractAction {

        public RedoAction() {
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK));
            putValue(NAME, "Redo");
            putValue(MNEMONIC_KEY, KeyEvent.VK_R);
            putValue(SHORT_DESCRIPTION, "Reapply the previous undo");
            putValue(SMALL_ICON, ResourceUtil.getIcon("images/actions/edit-redo.png"));
        }

        public void actionPerformed(ActionEvent e) {
            autoWindow.getAutoData().getCommandCenter().redo();
        }

    }

}
