package com.module.repository;

import com.module.model.entity.DisplacementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisplacementRepository extends JpaRepository<DisplacementEntity, Integer> {
}
