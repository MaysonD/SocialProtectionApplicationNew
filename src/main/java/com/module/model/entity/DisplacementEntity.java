package com.module.model.entity;

import com.module.xml.adapters.LocalDateAdapter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "displacements")
public class DisplacementEntity {

    @Column(name = "arrived_date")
    private LocalDate arrivedDate;

    @Column(name = "arrived_place")
    private String arrivedPlace;

    @Column(name = "decreased_date")
    private LocalDate decreasedDate;

    @Column(name = "decreased_place")
    private String decreasedPlace;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "com.module.helpers.UuidAutoGenerator")
    @Column(name = "uuid")
    private UUID uuid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "veteran_uuid")
    private VeteranEntity veteran;

    public DisplacementEntity() {
        this(null, null, null, null, null);
    }

    public DisplacementEntity(LocalDate arrivedDate, String arrivedPlace, LocalDate decreasedDate, String decreasedPlace, VeteranEntity veteran) {
        this.arrivedDate = arrivedDate;
        this.arrivedPlace = arrivedPlace;
        this.decreasedDate = decreasedDate;
        this.decreasedPlace = decreasedPlace;
        this.veteran = veteran;
    }

    public boolean equals(Object object) {
        if (object == this)
            return true;
        if ((object == null) || !(object instanceof DisplacementEntity))
            return false;

        final DisplacementEntity b = (DisplacementEntity) object;

        return uuid != null && b.getUuid() != null && uuid.equals(b.getUuid());
    }

    public LocalDate getArrivedDate() {
        return arrivedDate;
    }

    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    public void setArrivedDate(LocalDate arrivedDate) {
        this.arrivedDate = arrivedDate;
    }

    public String getArrivedPlace() {
        return arrivedPlace;
    }

    public void setArrivedPlace(String arrivedPlace) {
        this.arrivedPlace = arrivedPlace;
    }

    public LocalDate getDecreasedDate() {
        return decreasedDate;
    }

    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    public void setDecreasedDate(LocalDate decreasedDate) {
        this.decreasedDate = decreasedDate;
    }

    public String getDecreasedPlace() {
        return decreasedPlace;
    }

    public void setDecreasedPlace(String decreasedPlace) {
        this.decreasedPlace = decreasedPlace;
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
        return "Перемещение: " + arrivedDate + " " + arrivedPlace + " " + decreasedDate + " " + decreasedPlace;
    }
}
