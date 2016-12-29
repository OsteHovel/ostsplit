package com.ostsoft.smsplit.display;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class AutoMenu extends JMenuBar {
    public AutoMenu(AutoWindow autoWindow) {
        super();

        JMenu mnFile = new JMenu("File");
        mnFile.setMnemonic(KeyEvent.VK_F);
        add(mnFile);
        {
            mnFile.add(new JMenuItem(autoWindow.getAutoActions().getLoadAction()));
            mnFile.add(new JMenuItem(autoWindow.getAutoActions().getSaveAction()));
            mnFile.add(new JMenuItem(autoWindow.getAutoActions().getSaveAsAction()));

            mnFile.add(new JSeparator());

            mnFile.add(new JMenuItem(autoWindow.getAutoActions().getCloseAction()));

            mnFile.add(new JSeparator());

            mnFile.add(new JMenuItem(autoWindow.getAutoActions().getExitAction()));
        }

        JMenu mnEdit = new JMenu("Edit");
        mnEdit.setMnemonic(KeyEvent.VK_E);
        add(mnEdit);
        {
            mnEdit.add(new JMenuItem(autoWindow.getAutoActions().getUndoAction()));
            mnEdit.add(new JMenuItem(autoWindow.getAutoActions().getRedoAction()));
            mnEdit.add(new JSeparator());
        }

        JMenu mnCapture = new JMenu("Capture");
        mnEdit.setMnemonic(KeyEvent.VK_C);
        add(mnCapture);
        {
            mnCapture.add(new JCheckBoxMenuItem(autoWindow.getAutoActions().getPauseAction()));
        }

        JMenu mnHelp = new JMenu("Help");
        mnHelp.setMnemonic(KeyEvent.VK_H);
        add(mnHelp);
        {
            JMenuItem mntmAbout = new JMenuItem("About");
            mntmAbout.setMnemonic(KeyEvent.VK_A);
            mntmAbout.addActionListener(e -> new About());
            mnHelp.add(mntmAbout);
        }
    }
}
