package com.ostsoft.games.ostsplit.display;

import com.ostsoft.games.ostsplit.AutoData;
import com.ostsoft.games.ostsplit.display.panel.AutoPanel;
import com.ostsoft.games.ostsplit.display.panel.PanelData;
import com.ostsoft.games.ostsplit.display.panel.PanelEvent;

import javax.swing.JTextPane;
import java.awt.BorderLayout;
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

