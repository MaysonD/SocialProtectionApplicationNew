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
@Table(name = "veteran_honors")
public class VeteranHonorEntity {

    @Column(name = "date_of_receiving")
    private LocalDate dateOfReceiving;

    @Column(name = "decree")
    private String decree;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne()
    @JoinColumn(name = "honor_uuid")
    private HonorEntity honor;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "com.module.helpers.UuidAutoGenerator")
    @Column(name = "uuid")
    private UUID uuid;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne()
    @JoinColumn(name = "veteran_uuid")
    private VeteranEntity veteran;

    public VeteranHonorEntity() {
        this(null, null, null, null);
    }

    public VeteranHonorEntity(LocalDate dateOfReceiving, String decree, VeteranEntity veteran, HonorEntity honor) {
        this.dateOfReceiving = dateOfReceiving;
        this.decree = decree;
        this.honor = honor;
        this.veteran = veteran;
    }

    public boolean equals(Object object) {
        if (object == this)
            return true;
        if ((object == null) || !(object instanceof VeteranHonorEntity))
            return false;

        final VeteranHonorEntity b = (VeteranHonorEntity) object;

        return uuid != null && b.getUuid() != null && uuid.equals(b.getUuid());
    }

    public LocalDate getDateOfReceiving() {
        return this.dateOfReceiving;
    }

    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    public void setDateOfReceiving(LocalDate dateOfRecieving) {
        this.dateOfReceiving = dateOfRecieving;
    }

    public String getDecree() {
        return decree;
    }

    public void setDecree(String order) {
        this.decree = order;
    }

    public HonorEntity getHonor() {
        return honor;
    }

    public void setHonor(HonorEntity honor) {
        this.honor = honor;
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
        return "Награда: " + dateOfReceiving + " " + decree;
    }
}
