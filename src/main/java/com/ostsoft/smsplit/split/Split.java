package com.ostsoft.smsplit.split;

import com.ostsoft.smsplit.xml.config.ItemBox;

import java.util.Set;

public interface Split {
    void itemSplit(Set<ItemBox> itemsToSplitOn);
}
