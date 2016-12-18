package com.ostsoft.smsplit.display;

import com.ostsoft.smsplit.AutoData;
import com.ostsoft.smsplit.display.panel.AutoPanel;
import com.ostsoft.smsplit.display.panel.PanelData;
import com.ostsoft.smsplit.display.panel.PanelEvent;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LogPanel extends AutoPanel {
    private final JTextPane textPane = new JTextPane();

    public LogPanel(AutoData autoData) {
        super(autoData, new PanelData(autoData));
        setLayout(new BorderLayout(0, 0));
        this.add(textPane, BorderLayout.CENTER);

        Logger logger = LogManager.getLogManager().getLogger("");
        logger.addHandler(new LogHandler(textPane));
        logger.setLevel(Level.CONFIG);

    }

    @Override
    public void handlePanelEvent(PanelEvent panelEvent) {
        super.handlePanelEvent(panelEvent);
        if (panelEvent == PanelEvent.CLOSEPANEL) {
        }
    }

}

