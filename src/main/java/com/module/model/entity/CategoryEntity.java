package com.module.model.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "categories")
public class CategoryEntity {

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "category", targetEntity = SubcategoryEntity.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<SubcategoryEntity> subcategories = new LinkedList<>();

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "com.module.helpers.UuidAutoGenerator")
    @Column(name = "uuid")
    private UUID uuid;

    public CategoryEntity() {
        this(null);
    }

    public CategoryEntity(String name) {
        this.name = name;
    }

    public boolean equals(Object object) {
        if (object == this)
            return true;
        if ((object == null) || !(object instanceof CategoryEntity))
            return false;

        final CategoryEntity b = (CategoryEntity) object;

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

    public List<SubcategoryEntity> getSubcategories() {
        return subcategories;
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

    void addSubcategory(SubcategoryEntity subcategoryEntity, boolean set) {
        if (subcategoryEntity != null) {
            List<SubcategoryEntity> subcategories = this.getSubcategories();
            if (subcategories.contains(subcategoryEntity)) {
                subcategories.remove(subcategoryEntity);
                subcategories.add(subcategoryEntity);
            } else {
                subcategories.add(subcategoryEntity);
            }
            if (set) {
                subcategoryEntity.setCategory(this, false);
            }
        }
    }
}
