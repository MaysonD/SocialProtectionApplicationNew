package com.module.model.entity;

import com.module.xml.adapters.LocalDateAdapter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "family_members")
public class FamilyMemberEntity {

    @Column(name = "address")
    private String address;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "relation_degree")
    private String relationDegree;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "com.module.helpers.UuidAutoGenerator")
    @Column(name = "uuid")
    private UUID uuid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "veteran_uuid")
    private VeteranEntity veteran;

    public FamilyMemberEntity() {
        this(null, null, null, null, null, null);
    }

    public FamilyMemberEntity(String relationDegree, String fullName, String address, String phoneNumber, LocalDate dateOfBirth, VeteranEntity veteran) {
        this.relationDegree = relationDegree;
        this.dateOfBirth = dateOfBirth;
        this.fullName = fullName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.veteran = veteran;
    }

    public boolean equals(Object object) {
        if (object == this)
            return true;
        if ((object == null) || !(object instanceof FamilyMemberEntity))
            return false;

        final FamilyMemberEntity b = (FamilyMemberEntity) object;

        return uuid != null && b.getUuid() != null && uuid.equals(b.getUuid());
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRelationDegree() {
        return relationDegree;
    }

    public void setRelationDegree(String relationDegree) {
        this.relationDegree = relationDegree;
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
        return "Родственник: " + relationDegree + " " + fullName + " " + address + " " + dateOfBirth + " " + phoneNumber;
    }
}
