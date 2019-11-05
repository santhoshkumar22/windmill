package com.windmillfarm.management.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.windmillfarm.management.entity.WindMillDetailEntity;
import com.windmillfarm.management.exception.WindMillFarmException;
import com.windmillfarm.management.model.Location;
import com.windmillfarm.management.model.WindMillPowerData;
import com.windmillfarm.management.model.WindMillRequest;
import com.windmillfarm.management.repository.WindMillDetailsRepository;
import com.windmillfarm.management.repository.WindMillPowerRepository;

@SpringBootTest
public class WindMillFarmServiceTest {

	@Mock
	private WindMillDetailsRepository windMillDetailsRepository;

	@Mock
	private WindMillPowerRepository windMillPowerRepository;

	@InjectMocks
	private WindMillFarmService farmService;

	private String uniqueId = "WINDMILL00000001";
	private WindMillRequest windMillRequest;

	@BeforeEach
	public void init() {
		windMillRequest = new WindMillRequest();
		windMillRequest.setName("name");
		windMillRequest.setAddress("address");
		windMillRequest.setUniqueId(uniqueId);
		windMillRequest.setLocation(new Location("12.12", "13.13"));

		when(windMillDetailsRepository.findByUniqueId(uniqueId)).thenReturn(Optional.of(new WindMillDetailEntity()));
	}

	@Test
	public void test_registerWindMill_withValidInput() throws WindMillFarmException {
		windMillRequest.setUniqueId("WINDMILL00000011");
		
		when(windMillDetailsRepository.findByUniqueId("WINDMILL00000011")).thenReturn(Optional.empty());
		when(windMillDetailsRepository.save(any(WindMillDetailEntity.class)))
		.thenReturn(any(WindMillDetailEntity.class));
		String status = farmService.registerWindMill(windMillRequest);

		assertEquals("Windmill registered successfully", status);
	}

	@Test
	public void test_registerWindMill_withRegisteredInput() {
		assertThrows(WindMillFarmException.class, () -> farmService.registerWindMill(windMillRequest),
				"Windmill with unique id " + uniqueId + " is already registered");
	}

	@Test
	public void test_registerWindMill_withNullId() {
		windMillRequest.setUniqueId(null);

		assertThrows(WindMillFarmException.class, () -> farmService.registerWindMill(windMillRequest),
				"Unique Id/Name is missing in the request");
	}
	
	@Test
	public void test_registerWindMill_withNullName() {
		windMillRequest.setName(null);

		assertThrows(WindMillFarmException.class, () -> farmService.registerWindMill(windMillRequest),
				"Unique Id/Name is missing in the request");
	}
	
	@Test
	public void test_registerWindMill_withInvalidId() {
		windMillRequest.setUniqueId("WindMill");

		assertThrows(WindMillFarmException.class, () -> farmService.registerWindMill(windMillRequest),
				"Unique Id is in invalid format");
	}
	
	@Test
	public void test_savePower_withValidInput() {
		Integer power = 2222;

		when(windMillDetailsRepository.save(any(WindMillDetailEntity.class)))
		.thenReturn(any(WindMillDetailEntity.class));
		boolean status = farmService.savePower(uniqueId, power);
		
		assertEquals(true, status);
	}
	
	@Test
	public void test_savePower_withNullPower() {
		Integer power = null;

		boolean status = farmService.savePower(uniqueId, power);
		
		assertEquals(false, status);
	}
	
	@Test
	public void test_savePower_withNullId() {
		Integer power = 2222;

		boolean status = farmService.savePower(null, power);
		
		assertEquals(false, status);
	}
	
	@Test
	public void test_savePower_withNonRegisteredId() {
		Integer power = 2222;
		
		when(windMillDetailsRepository.findByUniqueId("WINDMILL00000011")).thenReturn(Optional.empty());
		boolean status = farmService.savePower("WINDMILL00000011", power);
		
		assertEquals(false, status);
	}
	
	@Test
	public void test_getHistoricalData_withValidInput() throws WindMillFarmException {
		List<WindMillPowerData> powerDataList = new ArrayList<>();
		powerDataList.add(new WindMillPowerData(LocalDate.now(), uniqueId, 123456L, 10000.00, 100000, 23456));
		
		when(windMillPowerRepository.findGroupBy(uniqueId, LocalDate.now())).thenReturn(powerDataList);
		List<WindMillPowerData> response = farmService.getHistoricalData(uniqueId);
		
		assertEquals(powerDataList, response);
	}
	
	@Test
	public void test_getHistoricalData_withNonRegisteredId() throws WindMillFarmException {
		when(windMillDetailsRepository.findByUniqueId("WINDMILL00000011")).thenReturn(Optional.empty());
		
		assertThrows(WindMillFarmException.class, () -> farmService.getHistoricalData("WINDMILL00000011"),
				"Windmill with unique id WINDMILL00000011 is not registered.");
	}

}
