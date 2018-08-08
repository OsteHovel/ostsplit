package com.ostsoft.games.ostsplit.xml.config;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

public class RectangleXML {
    @XmlAttribute
    public double x, y, width, height;

    @XmlAttribute
    public int r, g, b, tolerance;

    @XmlAttribute
    public boolean invert = false;

    @XmlTransient
    public boolean matching = false;

}
