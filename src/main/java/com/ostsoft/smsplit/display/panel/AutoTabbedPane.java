package com.ostsoft.smsplit.display.panel;

import javax.swing.*;
import java.awt.*;

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


