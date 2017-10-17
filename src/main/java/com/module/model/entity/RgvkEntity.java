package com.module.model.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "rgvk")
public class RgvkEntity {

    @Column(name = "name")
    private String name;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "com.module.helpers.UuidAutoGenerator")
    @Column(name = "uuid")
    private UUID uuid;

    public RgvkEntity() {
        this(null);
    }

    public RgvkEntity(String name) {
        this.name = name;
    }

    public boolean equals(Object object) {
        if (object == this)
            return true;
        if ((object == null) || !(object instanceof RgvkEntity))
            return false;

        final RgvkEntity b = (RgvkEntity) object;

        if (uuid != null && b.getUuid() != null) {
            return uuid.equals(b.getUuid());
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String toString() {
        return this.name;
    }
}
