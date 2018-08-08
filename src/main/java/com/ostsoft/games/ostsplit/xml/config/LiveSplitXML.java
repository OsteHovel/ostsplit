package com.ostsoft.games.ostsplit.xml.config;

import javax.xml.bind.annotation.XmlAttribute;

public class LiveSplitXML {
    @XmlAttribute
    public String host = "127.0.0.1";

    @XmlAttribute
    public int port = 16834;

    @XmlAttribute
    public int timeout = 1000;
}
