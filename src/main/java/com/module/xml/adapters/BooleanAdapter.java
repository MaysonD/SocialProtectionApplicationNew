package com.module.xml.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class BooleanAdapter extends XmlAdapter<String, Boolean> {

    public String marshal(Boolean bool) throws Exception {
        return bool ? "true" : "false";
    }

    public Boolean unmarshal(String boolString) throws Exception {
        return boolString.equals("true");
    }
}