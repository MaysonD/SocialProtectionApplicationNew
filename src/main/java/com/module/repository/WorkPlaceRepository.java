package com.module.repository;

import com.module.model.entity.WorkPlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkPlaceRepository extends JpaRepository<WorkPlaceEntity, Integer> {
}
