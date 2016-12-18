package com.ostsoft.smsplit.xml.config;

import com.ostsoft.smsplit.capture.CaptureMethod;

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
