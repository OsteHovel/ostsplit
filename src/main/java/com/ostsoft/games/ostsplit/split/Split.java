package com.ostsoft.games.ostsplit.split;

import com.ostsoft.games.ostsplit.xml.config.ItemBox;

import java.util.Set;

public interface Split {
    void itemSplit(Set<ItemBox> itemsToSplitOn);
}
