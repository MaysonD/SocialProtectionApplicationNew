package com.module.model.entity;

import com.module.xml.adapters.LocalDateAdapter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "help")
public class HelpEntity {

    @Column(name = "base")
    private String baseToHelp;

    @Column(name = "date")
    private LocalDate helpDate;

    @Column(name = "organization_name")
    private String organizationName;

    @Column(name = "type")
    private String helpType;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "com.module.helpers.UuidAutoGenerator")
    @Column(name = "uuid")
    private UUID uuid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "veteran_uuid")
    private VeteranEntity veteran;

    public HelpEntity() {
        this(null, null, null, null, null, null);
    }

    public HelpEntity(String base, String organizationName, String baseToHelp, String helpType, LocalDate helpDate, VeteranEntity veteran) {
        this.organizationName = organizationName;
        this.baseToHelp = baseToHelp;
        this.helpType = helpType;
        this.helpDate = helpDate;
        this.veteran = veteran;
    }

    public boolean equals(Object object) {
        if (object == this)
            return true;
        if ((object == null) || !(object instanceof HelpEntity))
            return false;

        final HelpEntity b = (HelpEntity) object;

        return uuid != null && b.getUuid() != null && uuid.equals(b.getUuid());
    }

    public String getBaseToHelp() {
        return baseToHelp;
    }

    public void setBaseToHelp(String baseToHelp) {
        this.baseToHelp = baseToHelp;
    }

    public LocalDate getHelpDate() {
        return helpDate;
    }

    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    public void setHelpDate(LocalDate helpDate) {
        this.helpDate = helpDate;
    }

    public String getHelpType() {
        return helpType;
    }

    public void setHelpType(String helpType) {
        this.helpType = helpType;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
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
}
