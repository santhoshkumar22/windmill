package com.windmillfarm.management.scheduler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.windmillfarm.management.service.WindMillFarmService;

@Component
public class WindMillFarmScheduler {

	@Autowired
	ResourceLoader resourceLoader;

	@Autowired
	WindMillFarmService farmService;

	private static final Logger logger = (Logger) LoggerFactory.getLogger(WindMillFarmScheduler.class);

	private final String fileName = "scheduler/Input.txt";
	private final String delimitter = ":";

	@Scheduled(fixedDelayString = "${scheduler.fixedDelay.milliseconds}")
	public void savePower() {
		logger.info("Inside scheduler to save power");
		try {
			Resource resource = resourceLoader.getResource("classpath:" + fileName);

			if (resource.exists()) {
				logger.info("Resource exists and started processing");
				InputStream input = resource.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(input));
				reader.lines().forEach(line -> {
					String[] arr = line.split(delimitter);
					farmService.savePower(arr[0], Integer.valueOf(arr[1]));

					// delete file content
					try {
						BufferedWriter writer = Files.newBufferedWriter(Paths.get(resource.getFile().toURI()));
						writer.write("");
						writer.flush();
					} catch (IOException ex) {
						logger.error("Error occurred while deleting the content of Input file", ex);
					}

				});
			}
		} catch (IOException ex) {
			logger.error("Error occurred while saving the Power in scheduler", ex);
		}
	}

}
