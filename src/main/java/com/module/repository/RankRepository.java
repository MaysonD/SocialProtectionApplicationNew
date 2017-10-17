package com.module.repository;

import com.module.model.entity.RankEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RankRepository extends JpaRepository<RankEntity, Integer> {
    RankEntity findByName(String name);
}
