package com.ostsoft.games.ostsplit;

import com.ostsoft.games.ostsplit.capture.Capture;
import com.ostsoft.games.ostsplit.capture.nullcapture.NullCapture;
import com.ostsoft.games.ostsplit.observer.Observable;
import com.ostsoft.games.ostsplit.observer.command.CommandCenter;
import com.ostsoft.games.ostsplit.split.NullSplit;
import com.ostsoft.games.ostsplit.split.Split;
import com.ostsoft.games.ostsplit.xml.config.ConfigXML;
import com.ostsoft.games.ostsplit.xml.config.ItemBox;
import com.ostsoft.games.ostsplit.xml.config.RectangleXML;

import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

public class AutoData extends Observable {
    private final CommandCenter commandCenter = new CommandCenter(this);
    public ConfigXML config;
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
}
