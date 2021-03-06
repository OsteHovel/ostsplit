package com.ostsoft.games.ostsplit.xml.config;

import javax.xml.bind.annotation.XmlAttribute;

public class WindowCaptureXML {
    @XmlAttribute
    public String windowTitle = "OBS";

    @XmlAttribute
    public boolean partial = false;

    @XmlAttribute
    public int x = 0;

    @XmlAttribute
    public int y = 0;

    @XmlAttribute
    public int width = 1;

    @XmlAttribute
    public int height = 1;
}
