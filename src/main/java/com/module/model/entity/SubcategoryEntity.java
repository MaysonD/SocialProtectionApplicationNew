package com.module.model.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.util.UUID;

@Entity
@Table(name = "subcategories")
public class SubcategoryEntity {

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "category_uuid")
    private CategoryEntity category;

    @Column(name = "name")
    private String name;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "com.module.helpers.UuidAutoGenerator")
    @Column(name = "uuid")
    private UUID uuid;

    public SubcategoryEntity() {
        this(null);
    }

    public SubcategoryEntity(String name) {
        this.name = name;
    }

    public boolean equals(Object object) {
        if (object == this)
            return true;
        if ((object == null) || !(object instanceof SubcategoryEntity))
            return false;

        final SubcategoryEntity b = (SubcategoryEntity) object;

        return uuid != null && b.getUuid() != null && uuid.equals(b.getUuid());
    }

    public CategoryEntity getCategory() {
        return category;
    }

    @XmlTransient
    public void setCategory(CategoryEntity category) {
        setCategory(category, true);
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

    void setCategory(CategoryEntity category, boolean add) {
        this.category = category;
        if (category != null && add) {
            category.addSubcategory(this, false);
        }
    }
}
