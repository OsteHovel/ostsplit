package com.ostsoft.games.ostsplit.xml.config;

import com.ostsoft.games.ostsplit.split.SplitMethod;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Splitters {
    @XmlAttribute
    public SplitMethod method = SplitMethod.LIVESPLIT;

    @XmlElement(name = "livesplit")
    public LiveSplitXML liveSplit = new LiveSplitXML();

    @XmlElement(name = "hotkeysplit")
    public HotkeySplitXML hotkeySplit = new HotkeySplitXML();
}
