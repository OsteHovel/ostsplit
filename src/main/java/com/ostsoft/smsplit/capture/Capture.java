package com.ostsoft.smsplit.capture;

import javax.swing.*;
import java.awt.image.BufferedImage;

public interface Capture {
    BufferedImage capture();

    JPanel getSettingsPanel();

    void autoAdjust();
}
