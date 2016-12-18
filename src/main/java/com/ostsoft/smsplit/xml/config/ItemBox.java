package com.ostsoft.smsplit.xml.config;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

public class ItemBox {
    @XmlAttribute
    public String name = "";

    @XmlAttribute
    public Action action = Action.SPLIT;

    @XmlAttribute
    public int duration = 4000;

    @XmlElement(name = "rectangle")
    public List<RectangleXML> rectangles;

    @XmlTransient
    public long timeSinceFirstMatch = 0;

    @Override
    public String toString() {
        return name;
    }
}
