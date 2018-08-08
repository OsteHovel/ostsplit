package com.ostsoft.games.ostsplit;

import com.ostsoft.games.ostsplit.capture.Capture;
import com.ostsoft.games.ostsplit.capture.nullcapture.NullCapture;
import com.ostsoft.games.ostsplit.observer.EventType;
import com.ostsoft.games.ostsplit.observer.Observable;
import com.ostsoft.games.ostsplit.observer.command.CommandCenter;
import com.ostsoft.games.ostsplit.split.NullSplit;
import com.ostsoft.games.ostsplit.split.Split;
import com.ostsoft.games.ostsplit.xml.XMLUtil;
import com.ostsoft.games.ostsplit.xml.config.ConfigXML;
import com.ostsoft.games.ostsplit.xml.config.ItemBox;
import com.ostsoft.games.ostsplit.xml.config.RectangleXML;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.prefs.Preferences;

public class AutoData extends Observable {
    private final CommandCenter commandCenter = new CommandCenter(this);
    public ConfigXML config;
    private String path;
    private boolean configChanged = false;

    private Capture capture = new NullCapture();
    private Split splitter = new NullSplit();

    private BufferedImage gameImage;
    private ItemBox selectedItemBox = null;
    private Set<RectangleXML> selectedItemBoxRectangles = new HashSet<>();
    private boolean capturePaused = false;

    public CommandCenter getCommandCenter() {
        return commandCenter;
    }

    public boolean isConfigChanged() {
        return configChanged;
    }

    public void setConfigChanged(boolean configChanged) {
        this.configChanged = configChanged;
    }

    public BufferedImage getGameImage() {
        return gameImage;
    }

    public void setGameImage(BufferedImage gameImage) {
        this.gameImage = gameImage;
    }

    public ItemBox getSelectedItemBox() {
        return selectedItemBox;
    }

    public void setSelectedItemBox(ItemBox selectedItemBox) {
        this.selectedItemBox = selectedItemBox;
    }

    public Set<RectangleXML> getSelectedItemBoxRectangles() {
        return selectedItemBoxRectangles;
    }

    public Capture getCapture() {
        return capture;
    }

    public void setCapture(Capture capture) {
        this.capture = capture;
    }

    public Split getSplitter() {
        return splitter;
    }

    public void setSplitter(Split splitter) {
        this.splitter = splitter;
    }

    public boolean isCapturePaused() {
        return capturePaused;
    }

    public void setCapturePaused(boolean capturePaused) {
        this.capturePaused = capturePaused;
    }

    public void load(boolean initialLoad) {
        Preferences preferences = Preferences.userNodeForPackage(OstSplit.class);
        if (initialLoad) {
            path = preferences.get("configLocation", "config.xml");
        }
        else {
            final JFileChooser fileChooser = new JFileChooser();
            fileChooser.setSelectedFile(new File(path));
            int returnVal = fileChooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                path = fileChooser.getSelectedFile().getAbsolutePath();
            }
        }
        config = XMLUtil.decodeConfig(path);
        if (config == null) {
            JOptionPane.showMessageDialog(null, path + " was not found, reverting to empty configuration", OstSplit.TITLE, JOptionPane.WARNING_MESSAGE);
            config = new ConfigXML();
        }
        else {
            preferences.put("configLocation", path);
        }

        configChanged = false;
        fireEvent(EventType.CONFIG_UPDATE);
        fireEvent(EventType.STATUS_BAR_MESSAGE, "Loaded " + path);
    }

    public void save(boolean saveAs) {
        if (saveAs || path == null) {
            final JFileChooser fileChooser = new JFileChooser();
            fileChooser.setSelectedFile(new File(path));
            int returnVal = fileChooser.showSaveDialog(null);
            if (returnVal != JFileChooser.APPROVE_OPTION) {
                return;
            }
            path = fileChooser.getSelectedFile().getAbsolutePath();
        }
        XMLUtil.encodeConfig(path, config);
        configChanged = false;
        fireEvent(EventType.CONFIG_UPDATE);
        fireEvent(EventType.STATUS_BAR_MESSAGE, "Saved " + path);

        Preferences preferences = Preferences.userNodeForPackage(OstSplit.class);
        preferences.put("configLocation", path);
    }
}
