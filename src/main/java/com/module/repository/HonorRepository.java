package com.module.repository;

import com.module.model.entity.HonorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HonorRepository extends JpaRepository<HonorEntity, Integer> {
    HonorEntity findByName(String name);
}
