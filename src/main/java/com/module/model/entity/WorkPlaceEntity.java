package com.module.model.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.util.UUID;

@Entity
@Table(name = "work_places")
public class WorkPlaceEntity {

    @Column(name = "hr_number")
    private String hrNumber;

    @Column(name = "locality")
    private String locality;

    @Column(name = "organization")
    private String organization;

    @Column(name = "position")
    private String position;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "com.module.helpers.UuidAutoGenerator")
    @Column(name = "uuid")
    private UUID uuid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "veteran_uuid")
    private VeteranEntity veteran;

    public WorkPlaceEntity() {
        this(null, null, null, null, null);
    }

    public WorkPlaceEntity(String organization, String locality, String position, String hrNumber, VeteranEntity veteran) {
        this.organization = organization;
        this.locality = locality;
        this.position = position;
        this.hrNumber = hrNumber;
        this.veteran = veteran;
    }

    public boolean equals(Object object) {
        if (object == this)
            return true;
        if ((object == null) || !(object instanceof WorkPlaceEntity))
            return false;

        final WorkPlaceEntity b = (WorkPlaceEntity) object;

        return uuid != null && b.getUuid() != null && uuid.equals(b.getUuid());
    }

    public String getHrNumber() {
        return hrNumber;
    }

    public void setHrNumber(String hrNumber) {
        this.hrNumber = hrNumber;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String post) {
        this.position = post;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public VeteranEntity getVeteran() {
        return veteran;
    }

    @XmlTransient
    public void setVeteran(VeteranEntity veteran) {
        this.veteran = veteran;
    }

    public String toString() {
        return "Место работы: " + organization + " " + locality + " " + position + " " + hrNumber;
    }
}
