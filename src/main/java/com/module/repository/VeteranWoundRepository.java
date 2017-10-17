package com.module.repository;

import com.module.model.entity.VeteranWoundEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VeteranWoundRepository extends JpaRepository<VeteranWoundEntity, Integer> {
}
