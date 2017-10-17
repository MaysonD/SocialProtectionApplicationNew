package com.module.repository;

import com.module.model.entity.VeteranEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface VeteranRepository extends JpaRepository<VeteranEntity, Integer>, JpaSpecificationExecutor<VeteranEntity> {
}
