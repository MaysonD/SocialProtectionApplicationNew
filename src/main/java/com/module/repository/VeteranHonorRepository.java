package com.module.repository;

import com.module.model.entity.VeteranHonorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VeteranHonorRepository extends JpaRepository<VeteranHonorEntity, Integer> {
}
