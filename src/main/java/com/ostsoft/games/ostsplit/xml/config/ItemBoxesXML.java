package com.ostsoft.games.ostsplit.xml.config;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

public class ItemBoxesXML {
    @XmlAttribute(name = "matching")
    public boolean itemMatching = true;

    @XmlElement(name = "itembox")
    public List<ItemBox> itemBox = new ArrayList<>();

}

