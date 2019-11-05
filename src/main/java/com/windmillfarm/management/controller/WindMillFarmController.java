package com.windmillfarm.management.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.windmillfarm.management.exception.WindMillFarmException;
import com.windmillfarm.management.model.WindMillPowerData;
import com.windmillfarm.management.model.WindMillRequest;
import com.windmillfarm.management.service.WindMillFarmService;

@RestController
@RequestMapping("/api/windmill")
public class WindMillFarmController {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(WindMillFarmController.class);
	
	@Autowired
	private WindMillFarmService farmService;
	
	@PostMapping(value = "/register")
	public ResponseEntity<String> windMillRegister(@RequestBody WindMillRequest request) {
		logger.info("Inside Register Method");
		String status;
		try{
		status = farmService.registerWindMill(request);
		}catch(WindMillFarmException farmException){
			status = farmException.getMessage();
			return new ResponseEntity<String>(status, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(status, HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/getWindmill/{uid}")
	public ResponseEntity<Object> getWindMillDetails(@PathVariable("uid") String uid) {
		logger.info("Inside get WindMill Details method");
		Optional<WindMillRequest> windMillrequest = farmService.getWindMillDetailsByUid(uid);
		if(windMillrequest.isPresent())
			return new ResponseEntity<>(windMillrequest.get(), HttpStatus.OK);
		else
			return new ResponseEntity<Object>("No Windmill present", HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(value = "/getHistoricalData/{uid}")
	public ResponseEntity<Object> getHistoricalData(@PathVariable("uid") String uid) {
		logger.info("Inside get WindMill historical data method");
		List<WindMillPowerData> powers;
		try {
			powers = farmService.getHistoricalData(uid);
		} catch (WindMillFarmException ex) {
			return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(powers, HttpStatus.OK);
	}

}
