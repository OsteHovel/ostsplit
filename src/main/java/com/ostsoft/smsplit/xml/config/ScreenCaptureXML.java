package com.ostsoft.smsplit.xml.config;

import javax.xml.bind.annotation.XmlAttribute;

public class ScreenCaptureXML {
    @XmlAttribute
    public int x = 0;

    @XmlAttribute
    public int y = 0;

    @XmlAttribute
    public int width = 1;

    @XmlAttribute
    public int height = 1;
}
