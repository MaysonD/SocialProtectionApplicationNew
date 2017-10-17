package com.module.repository;

import com.module.model.entity.WoundTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WoundTypeRepository extends JpaRepository<WoundTypeEntity, Integer> {
    WoundTypeEntity findByType(String type);
}
