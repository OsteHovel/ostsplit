package com.ostsoft.smsplit.split;

import com.ostsoft.smsplit.xml.config.HotkeySplitXML;
import com.ostsoft.smsplit.xml.config.ItemBox;

import java.awt.*;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HotkeySplit implements Split {
    private static Logger logger = Logger.getLogger(HotkeySplit.class.getName());
    private final HotkeySplitXML hotkeySplitXML;
    private Robot robot;

    public HotkeySplit(HotkeySplitXML hotkeySplitXML) {
        this.hotkeySplitXML = hotkeySplitXML;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

    }

    public void itemSplit(String itemName) {
        logger.log(Level.INFO, "Pressing " + hotkeySplitXML.itemSplitKeycode + " to split " + itemName);
        robot.keyPress(hotkeySplitXML.itemSplitKeycode);
        robot.delay(hotkeySplitXML.delay);
        robot.keyRelease(hotkeySplitXML.itemSplitKeycode);
    }

    @Override
    public void itemSplit(Set<ItemBox> itemsToSplitOn) {
        for (ItemBox itemBox : itemsToSplitOn) {
            itemSplit(itemBox.name);
        }
    }
}
