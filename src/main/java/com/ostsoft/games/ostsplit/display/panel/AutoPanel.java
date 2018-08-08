package com.ostsoft.games.ostsplit.display.panel;

import com.ostsoft.games.ostsplit.AutoData;

import javax.swing.JPanel;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

public abstract class AutoPanel extends JPanel {
    protected final AutoData autoData;
    protected final PanelData panelData;
    protected final List<Component> mnTools = new ArrayList<>();

    protected AutoPanel(AutoData autoData, PanelData panelData) {
        this.autoData = autoData;
        this.panelData = panelData;

    }

    public void handlePanelEvent(PanelEvent panelEvent) {
    }

    public List<Component> getToolsMenu() {
        return mnTools;
    }
}