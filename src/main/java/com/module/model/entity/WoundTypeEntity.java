package com.module.model.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "wound_types")
public class WoundTypeEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "com.module.helpers.UuidAutoGenerator")
    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "type")
    private String type;


    public WoundTypeEntity() {
        this(null);
    }

    public WoundTypeEntity(String type) {
        this.type = type;
    }

    public boolean equals(Object object) {
        if (object == this)
            return true;
        if ((object == null) || !(object instanceof WoundTypeEntity))
            return false;

        final WoundTypeEntity b = (WoundTypeEntity) object;

        return uuid != null && b.getUuid() != null && uuid.equals(b.getUuid());
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String toString() {
        return this.type;
    }
}
