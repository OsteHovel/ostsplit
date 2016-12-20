package com.ostsoft.smsplit.xml.config;

import com.ostsoft.smsplit.xml.config.action.Action;
import com.ostsoft.smsplit.xml.config.action.ActionXML;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.List;

public class ItemBox {
    @XmlAttribute
    public String name = "";

    @XmlAttribute
    public int duration = 4000;

    @XmlElement(name = "rectangle")
    public List<RectangleXML> rectangles = new ArrayList<>();

    @XmlElement(name = "action")
    public List<ActionXML> actions = new ArrayList<>();

    @XmlTransient
    public long timeSinceFirstMatch = 0;

    @XmlTransient
    public boolean matching = false;

    @Override
    public String toString() {
        return name;
    }
}
