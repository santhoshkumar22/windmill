package com.windmillfarm.management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.windmillfarm.management.entity.WindMillDetailEntity;

@Repository
public interface WindMillDetailsRepository extends JpaRepository<WindMillDetailEntity, Integer>{

	Optional<WindMillDetailEntity> findByUniqueId(String uniqueId);
}
