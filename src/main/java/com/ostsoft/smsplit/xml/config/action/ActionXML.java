package com.ostsoft.smsplit.xml.config.action;

import javax.xml.bind.annotation.XmlAttribute;

public class ActionXML {
    @XmlAttribute
    public String name = "";

    @XmlAttribute
    public Matching matching = Matching.CONTAINS_INSENSITIVE;

    @XmlAttribute
    public Action action = Action.SPLIT;

    public ActionXML(String name, Matching matching, Action action) {
        this.name = name;
        this.matching = matching;
        this.action = action;
    }

    public ActionXML() {
    }
}
