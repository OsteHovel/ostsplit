package com.ostsoft.games.ostsplit.capture;

import javax.swing.JPanel;
import java.awt.image.BufferedImage;

public interface Capture {
    BufferedImage capture();

    JPanel getSettingsPanel();

    void autoAdjust();
}
