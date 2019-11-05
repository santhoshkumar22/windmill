package com.windmillfarm.management.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.windmillfarm.management.entity.WindMillPowerEntity;
import com.windmillfarm.management.model.WindMillPowerData;

@Repository
public interface WindMillPowerRepository extends JpaRepository<WindMillPowerEntity, Integer>{

	List<WindMillPowerEntity> findByWindmillUniqueIdAndGeneratedDateBefore(String windmillUniqueId, LocalDate generatedDate);
	
	@Query("select new com.windmillfarm.management.model.WindMillPowerData(p.generatedDate as date, "
			+ "p.windmillUniqueId as uniqueId, "
			+ "sum(p.power) as sum, "
			+ "avg(p.power) as average, "
			+ "max(p.power) as maximum, "
			+ "min(p.power) as minimum) "
			+ "from WindMillPowerEntity p "
			+ "where p.windmillUniqueId = :uid "
			+ "and p.generatedDate < :curDate "			
			+ "group by p.generatedDate")
	List<WindMillPowerData> findGroupBy(String uid,LocalDate curDate);

}
