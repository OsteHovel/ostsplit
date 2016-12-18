package com.ostsoft.smsplit.capture.nullcapture;

import com.ostsoft.smsplit.capture.Capture;

import javax.swing.*;
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
