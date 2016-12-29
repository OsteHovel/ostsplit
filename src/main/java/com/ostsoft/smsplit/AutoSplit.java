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
import com.ostsoft.smsplit.xml.config.ItemBox;
import com.ostsoft.smsplit.xml.config.action.Action;
import com.ostsoft.smsplit.xml.config.action.ActionXML;
import com.ostsoft.smsplit.xml.config.action.Matching;

import javax.swing.*;
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

        Main.setCycleTime(autoData.config.cycleTime);
        addDefault(autoData.config);


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

    private void addDefault(ConfigXML config) {
        for (ItemBox itemBox : config.itemBoxes.itemBox) {
            if (itemBox.actions.isEmpty()) {
                itemBox.actions.add(new ActionXML(itemBox.name, Matching.CONTAINS_INSENSITIVE, Action.SPLIT));
            }
        }
    }

    public void cycle(long timeSinceLastUpdate) {
        if (!autoData.isCapturePaused()) {
            autoData.setGameImage(autoData.getCapture().capture());
        }
        autoData.fireEvent(EventType.UPDATED_GAME_IMAGE);
    }

    public AutoData getAutoData() {
        return autoData;
    }

    public AutoWindow getAutoWindow() {
        return autoWindow;
    }
}
