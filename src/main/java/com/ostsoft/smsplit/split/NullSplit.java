package com.ostsoft.smsplit.split;

import com.ostsoft.smsplit.xml.config.ItemBox;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NullSplit implements Split {
    private static Logger logger = Logger.getLogger(NullSplit.class.getName());

    @Override
    public void itemSplit(Set<ItemBox> itemsToSplitOn) {
        for (ItemBox itemBox : itemsToSplitOn) {
            logger.log(Level.WARNING, "NullSplit is a dummy, " + itemBox.name);
        }
    }
}