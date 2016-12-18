package com.ostsoft.smsplit;

import com.ostsoft.smsplit.capture.CaptureMethod;
import com.ostsoft.smsplit.capture.screencapture.ScreenCapture;
import com.ostsoft.smsplit.capture.windowcapture.WindowCapture;
import com.ostsoft.smsplit.display.AutoWindow;
import com.ostsoft.smsplit.observer.EventType;
import com.ostsoft.smsplit.split.HotkeySplit;
import com.ostsoft.smsplit.split.LiveSplit;
import com.ostsoft.smsplit.split.NullSplit;
import com.ostsoft.smsplit.xml.XMLUtil;
import com.ostsoft.smsplit.xml.config.ConfigXML;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AutoSplit {
    private static Logger logger = Logger.getLogger(AutoSplit.class.getName());
    private final AutoData autoData = new AutoData();
    private final AutoWindow autoWindow;
    private final ItemBoxSplitter itemBoxSplitter;

    public AutoSplit() {
        autoData.config = XMLUtil.decodeConfig("config.xml");
        if (autoData.config == null) {
            JOptionPane.showMessageDialog(null, "Missing config.xml", "Error", JOptionPane.ERROR_MESSAGE);
            autoData.config = new ConfigXML();
        }

        switch (autoData.config.splitters.method) {
            case LIVESPLIT:
                autoData.setSplitter(new LiveSplit(autoData.config));
                break;
            case HOTKEYSPLIT:
                autoData.setSplitter(new HotkeySplit(autoData.config.splitters.hotkeySplit));
                break;
            default:
                autoData.setSplitter(new NullSplit());
                break;
        }

        if (autoData.config.capture.method == CaptureMethod.SCREENCAPTURE) {
            autoData.setCapture(new ScreenCapture(autoData));
        } else if (autoData.config.capture.method == CaptureMethod.WINDOWCAPTURE) {
            autoData.setCapture(new WindowCapture(autoData));
        }


        autoWindow = new AutoWindow(autoData);
        itemBoxSplitter = new ItemBoxSplitter(autoData);
    }

    public void cycle(long timeSinceLastUpdate) {
        if (autoData.config.debug.capture) {
            logger.log(Level.INFO, "Capture");
        }
        BufferedImage gameImage = autoData.getCapture().capture();
        if (autoData.config.debug.capture) {
            logger.log(Level.INFO, "Captured");
        }
        autoData.setGameImage(gameImage);
        itemBoxSplitter.checkForBox(gameImage);
        autoData.fireEvent(EventType.UPDATED_GAME_IMAGE);
    }

    public AutoData getAutoData() {
        return autoData;
    }

    public AutoWindow getAutoWindow() {
        return autoWindow;
    }
}
