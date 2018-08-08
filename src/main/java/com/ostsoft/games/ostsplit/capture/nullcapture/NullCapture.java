package com.ostsoft.games.ostsplit.capture.nullcapture;

import com.ostsoft.games.ostsplit.capture.Capture;

import javax.swing.JPanel;
import java.awt.image.BufferedImage;

public class NullCapture implements Capture {
    @Override
    public BufferedImage capture() {
        return null;
    }

    @Override
    public JPanel getSettingsPanel() {
        return new NullCapturePanel();
    }

    @Override
    public void autoAdjust() {

    }
}
