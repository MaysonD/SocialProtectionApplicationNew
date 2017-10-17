package com.module.model.entity;

import com.module.xml.adapters.LocalDateAdapter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "veteran_wounds")
public class VeteranWoundEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "com.module.helpers.UuidAutoGenerator")
    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "date")
    private LocalDate date;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne()
    @JoinColumn(name = "veteran_uuid")
    private VeteranEntity veteran;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne()
    @JoinColumn(name = "type_uuid")
    private WoundTypeEntity woundType;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne()
    @JoinColumn(name = "disability_uuid")
    private WoundDisabilityEntity woundDisability;

    public VeteranWoundEntity() {
        this(null, null, null, null);
    }

    public VeteranWoundEntity(LocalDate date, VeteranEntity veteran, WoundTypeEntity woundType, WoundDisabilityEntity woundDisability) {
        this.date = date;
        this.veteran = veteran;
        this.woundType = woundType;
        this.woundDisability = woundDisability;
    }

    public boolean equals(Object object) {
        if (object == this)
            return true;
        if ((object == null) || !(object instanceof VeteranWoundEntity))
            return false;

        final VeteranWoundEntity b = (VeteranWoundEntity) object;

        return uuid != null && b.getUuid() != null && uuid.equals(b.getUuid());
    }

    public LocalDate getDate() {
        return date;
    }

    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    public void setDate(LocalDate date) {
        this.date = date;
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

    public WoundDisabilityEntity getWoundDisability() {
        return woundDisability;
    }

    public void setWoundDisability(WoundDisabilityEntity woundDisability) {
        this.woundDisability = woundDisability;
    }

    public WoundTypeEntity getWoundType() {
        return woundType;
    }

    public void setWoundType(WoundTypeEntity woundType) {
        this.woundType = woundType;
    }

    @Override
    public String toString() {
        return "VeteranWoundEntity{" +
                "date=" + date +
                ", woundType=" + woundType +
                ", woundDisability=" + woundDisability +
                '}';
    }
}
