package com.ostsoft.games.ostsplit.xml.config;

import com.ostsoft.games.ostsplit.capture.CaptureMethod;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class CaptureXML {
    @XmlAttribute
    public CaptureMethod method = CaptureMethod.NULLCAPTURE;

    @XmlElement(name = "screencapture")
    public ScreenCaptureXML screenCapture = new ScreenCaptureXML();

    @XmlElement(name = "windowcapture")
    public WindowCaptureXML windowCapture = new WindowCaptureXML();
}
