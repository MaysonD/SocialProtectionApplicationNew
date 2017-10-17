package com.module.repository;

import com.module.model.entity.RgvkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RgvkRepository extends JpaRepository<RgvkEntity, Integer> {
    RgvkEntity findByName(String name);
}
