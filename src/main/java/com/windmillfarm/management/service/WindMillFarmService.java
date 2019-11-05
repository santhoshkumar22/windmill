package com.windmillfarm.management.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.windmillfarm.management.entity.WindMillDetailEntity;
import com.windmillfarm.management.entity.WindMillPowerEntity;
import com.windmillfarm.management.exception.WindMillFarmException;
import com.windmillfarm.management.model.Location;
import com.windmillfarm.management.model.WindMillPowerData;
import com.windmillfarm.management.model.WindMillRequest;
import com.windmillfarm.management.repository.WindMillDetailsRepository;
import com.windmillfarm.management.repository.WindMillPowerRepository;

@Service
public class WindMillFarmService {
	
	@Autowired
	private WindMillDetailsRepository windMillDetailsRepository;
	
	@Autowired
	private WindMillPowerRepository windMillPowerRepository;
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(WindMillFarmService.class);
	
	public String registerWindMill(WindMillRequest request) throws WindMillFarmException{
		logger.debug("Registering a new windmill with name {}",request.getName());		
		
		if(StringUtils.isEmpty(request.getUniqueId()) || StringUtils.isEmpty(request.getName())){
			throw new WindMillFarmException("Unique Id/Name is missing in the request");
		}if(request.getUniqueId().trim().length()!=16) {
			throw new WindMillFarmException("Unique Id is in invalid format");
		}
		if(windMillDetailsRepository.findByUniqueId(request.getUniqueId()).isPresent()){
			throw new WindMillFarmException("Windmill with unique id "+request.getUniqueId()+" is already registered");
		}
		
		WindMillDetailEntity windMillDetailEntity = convertToWindMillDetailsEntity(request);
		windMillDetailsRepository.save(windMillDetailEntity);
		return "Windmill registered successfully";
	}
	
	public Optional<WindMillRequest> getWindMillDetailsByUid(String uniqueId){
		logger.debug("Get windmill details based on Unique id {}", uniqueId);
		Optional<WindMillDetailEntity> windMillDetailEntity = windMillDetailsRepository.findByUniqueId(uniqueId);
		if(windMillDetailEntity.isPresent())
			return Optional.of(convertToWindMillRequest(windMillDetailEntity.get()));
		else
			return Optional.empty();
		
	}
	
	public boolean savePower(String uniqueId, Integer power){
		logger.info("Saving power {} for windmill {}",power,uniqueId);
		if(!StringUtils.isEmpty(uniqueId) && power!=null){
			Optional<WindMillDetailEntity> windMillDetailEntity = windMillDetailsRepository.findByUniqueId(uniqueId);
			if(windMillDetailEntity.isPresent()){
				WindMillPowerEntity powerEntity = new WindMillPowerEntity();
				powerEntity.setPower(power);
				powerEntity.setGeneratedDate(LocalDate.now());
				powerEntity.setWindmillUniqueId(uniqueId);
				windMillPowerRepository.save(powerEntity);
				logger.info("saved");
				return true;
			}			
		}		
		return false;
	}
	
	public List<WindMillPowerData> getHistoricalData(String uniqueId) throws WindMillFarmException{
		logger.debug("Get historical data for windmill {}",uniqueId);
		Optional<WindMillDetailEntity> windMillDetailEntity = windMillDetailsRepository.findByUniqueId(uniqueId);
		List<WindMillPowerData> powerDataList = new ArrayList<>();
		if(windMillDetailEntity.isPresent()){			
			powerDataList = windMillPowerRepository.findGroupBy(uniqueId, LocalDate.now());
		}else {
			logger.debug("Windmill with unique id {} is not registered.",uniqueId);
			throw new WindMillFarmException("Windmill with unique id "+uniqueId+" is not registered.");
		}
		
		return powerDataList;
	}
	
	private WindMillDetailEntity convertToWindMillDetailsEntity(WindMillRequest request){
		WindMillDetailEntity windMillDetailEntity = new WindMillDetailEntity();
		windMillDetailEntity.setName(request.getName());
		windMillDetailEntity.setUniqueId(request.getUniqueId());
		windMillDetailEntity.setAddress(request.getAddress());
		Optional.ofNullable(request.getLocation()).ifPresent(location -> {
			windMillDetailEntity.setLatitude(location.getLatitude());
			windMillDetailEntity.setLongitude(location.getLongitude());
		});
		return windMillDetailEntity;
	}
	
	private WindMillRequest convertToWindMillRequest(WindMillDetailEntity detailsEntity){
		WindMillRequest windMillRequest = new WindMillRequest();
		windMillRequest.setName(detailsEntity.getName());
		windMillRequest.setAddress(detailsEntity.getAddress());
		windMillRequest.setUniqueId(detailsEntity.getUniqueId());
		windMillRequest.setLocation(new Location(detailsEntity.getLongitude(),detailsEntity.getLatitude()));
		return windMillRequest;
	}

}
