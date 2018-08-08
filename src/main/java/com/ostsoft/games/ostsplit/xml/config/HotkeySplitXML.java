package com.ostsoft.games.ostsplit.xml.config;

import javax.xml.bind.annotation.XmlAttribute;

public class HotkeySplitXML {
    @XmlAttribute
    public int itemSplitKeycode;

    @XmlAttribute
    public int delay = 20;
}
