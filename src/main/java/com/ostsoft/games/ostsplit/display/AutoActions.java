package com.ostsoft.games.ostsplit.display;


import com.ostsoft.games.ostsplit.observer.EventType;
import com.ostsoft.games.ostsplit.observer.Observer;
import com.ostsoft.games.ostsplit.util.ResourceUtil;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.KeyStroke;
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

    private final Action pauseAction = new PauseAction();

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

    public Action getPauseAction() {
        return pauseAction;
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
            autoWindow.getAutoData().load(false);
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
            autoWindow.getAutoData().save(false);
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
            autoWindow.getAutoData().save(true);
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
            autoWindow.exit();
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

    private class PauseAction extends AbstractAction {

        public PauseAction() {
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
            putValue(NAME, "Pause");
            putValue(MNEMONIC_KEY, KeyEvent.VK_P);
            putValue(SHORT_DESCRIPTION, "Pause capture");
//            putValue(SMALL_ICON, ResourceUtil.getIcon("images/actions/edit-redo.png"));
        }

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof JCheckBoxMenuItem) {
                JCheckBoxMenuItem source = (JCheckBoxMenuItem) e.getSource();
                autoWindow.getAutoData().setCapturePaused(source.getState());
            }
        }

    }

}
