package com.ostsoft.smsplit.xml.config.action;

import javax.xml.bind.annotation.XmlAttribute;

public class ActionXML {
    @XmlAttribute
    public Action action = Action.SPLIT;

    @XmlAttribute
    public String name = "";

    @XmlAttribute
    public Matching matching = Matching.CONTAINS_INSENSITIVE;

}
