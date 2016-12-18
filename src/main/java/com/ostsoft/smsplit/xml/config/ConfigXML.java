package com.ostsoft.smsplit.xml.config;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "smsplit")
public class ConfigXML {
    @XmlElement(name = "capture")
    public CaptureXML capture = new CaptureXML();

    @XmlElement(name = "splitters")
    public Splitters splitters = new Splitters();

    @XmlElement(name = "debug")
    public DebugXML debug = new DebugXML();

    @XmlElement(name = "itemboxes")
    public ItemBoxesXML itemBoxes = new ItemBoxesXML();


}
