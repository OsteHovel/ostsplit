package com.ostsoft.games.ostsplit;

import com.ostsoft.games.ostsplit.capture.CaptureMethod;
import com.ostsoft.games.ostsplit.capture.screencapture.ScreenCapture;
import com.ostsoft.games.ostsplit.capture.windowcapture.WindowCapture;
import com.ostsoft.games.ostsplit.display.AutoWindow;
import com.ostsoft.games.ostsplit.observer.EventType;
import com.ostsoft.games.ostsplit.split.HotkeySplit;
import com.ostsoft.games.ostsplit.split.LiveSplit;
import com.ostsoft.games.ostsplit.split.NullSplit;
import com.ostsoft.games.ostsplit.xml.XMLUtil;
import com.ostsoft.games.ostsplit.xml.config.ConfigXML;
import com.ostsoft.games.ostsplit.xml.config.ItemBox;
import com.ostsoft.games.ostsplit.xml.config.action.Action;
import com.ostsoft.games.ostsplit.xml.config.action.ActionXML;
import com.ostsoft.games.ostsplit.xml.config.action.Matching;

import javax.swing.JOptionPane;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

public class OstSplit {
    public static final String TITLE;
    private static Logger logger = Logger.getLogger(OstSplit.class.getName());
    private final AutoData autoData = new AutoData();
    private final AutoWindow autoWindow;
    private final ItemBoxSplitter itemBoxSplitter;

    static {
        if (OstSplit.class.getPackage().getSpecificationVersion() != null) {
            TITLE = OstSplit.class.getPackage().getSpecificationVersion();
        }
        else {
            TITLE = "DEVELOPMENT";
        }
    }

    public OstSplit() {
        Preferences preferences = Preferences.userNodeForPackage(OstSplit.class);
        String configLocation = preferences.get("configLocation", "config.xml");
        autoData.config = XMLUtil.decodeConfig(configLocation);
        if (autoData.config == null) {
            JOptionPane.showMessageDialog(null, configLocation + " was not found, starting with empty configuration", OstSplit.TITLE, JOptionPane.WARNING_MESSAGE);
            autoData.config = new ConfigXML();
        }

        RunOstSplit.setCycleTime(autoData.config.cycleTime);
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
        }
        else if (autoData.config.capture.method == CaptureMethod.WINDOWCAPTURE) {
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
