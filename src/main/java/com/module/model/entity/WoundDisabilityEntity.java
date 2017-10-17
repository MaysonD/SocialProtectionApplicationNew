package com.module.model.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "wound_disabilities")
public class WoundDisabilityEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "com.module.helpers.UuidAutoGenerator")
    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "disability")
    private String disability;


    public WoundDisabilityEntity() {
        this(null);
    }

    public WoundDisabilityEntity(String type) {
        this.disability = type;
    }

    public boolean equals(Object object) {
        if (object == this)
            return true;
        if ((object == null) || !(object instanceof WoundDisabilityEntity))
            return false;

        final WoundDisabilityEntity b = (WoundDisabilityEntity) object;

        return uuid != null && b.getUuid() != null && uuid.equals(b.getUuid());
    }

    public String getDisability() {
        return disability;
    }

    public void setDisability(String type) {
        this.disability = type;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String toString() {
        return this.disability;
    }
}
