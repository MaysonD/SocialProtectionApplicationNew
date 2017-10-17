package com.module.xml;

import com.module.model.entity.VeteranEntity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement
public class VeteransExchange {

    private List<VeteranEntity> veterans = new LinkedList<>();

    public List<VeteranEntity> getVeterans() {
        return veterans;
    }

    @XmlElement
    public void setVeterans(List<VeteranEntity> veterans) {
        this.veterans = veterans;
    }
}
