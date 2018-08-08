package com.ostsoft.games.ostsplit.display.panel;

import javax.swing.JTabbedPane;
import java.awt.Component;

public class AutoTabbedPane extends JTabbedPane {

    @Override
    public void removeTabAt(int index) {
        Component component = getComponentAt(index);
        if (component instanceof AutoPanel) {
            ((AutoPanel) component).handlePanelEvent(PanelEvent.CLOSEPANEL);
        }

        super.removeTabAt(index);
    }
}


