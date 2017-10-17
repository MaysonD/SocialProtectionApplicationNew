package com.module.repository;

import com.module.model.entity.MilitaryTermEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MilitaryTermRepository extends JpaRepository<MilitaryTermEntity, Integer> {
}
