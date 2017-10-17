package com.module.repository;

import com.module.model.entity.WoundDisabilityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WoundDisabilityRepository extends JpaRepository<WoundDisabilityEntity, Integer> {
    WoundDisabilityEntity findByDisability(String disability);
}
