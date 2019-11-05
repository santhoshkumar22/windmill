package com.windmillfarm.management.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.windmillfarm.management.exception.WindMillFarmException;
import com.windmillfarm.management.model.WindMillPowerData;
import com.windmillfarm.management.model.WindMillRequest;
import com.windmillfarm.management.service.WindMillFarmService;

@SpringBootTest
@AutoConfigureMockMvc
public class WindMillFarmControllerTest {

	@MockBean
	private WindMillFarmService farmService;

	@InjectMocks
	private WindMillFarmController controller;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void test_getWindMill_withValidInput() throws Exception {
		WindMillRequest windMillRequest = new WindMillRequest();
		windMillRequest.setName("name1");

		when(farmService.getWindMillDetailsByUid(eq("WINDMILL00000001"))).thenReturn(Optional.of(windMillRequest));
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/api/windmill/getWindmill/WINDMILL00000001")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content()
						.string(equalTo("{\"uniqueId\":null,\"name\":\"name1\",\"address\":null,\"location\":null}")));
	}

	@Test
	public void test_getWindMill_withInvalidInput() throws Exception {
		when(farmService.getWindMillDetailsByUid(eq("WINDMILL00000001"))).thenReturn(Optional.empty());
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/api/windmill/getWindmill/WINDMILL00000001")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andExpect(content().string(equalTo("No Windmill present")));
	}

	@Test
	public void test_windMillRegister_withValidInput() throws Exception {
		String request = "{\"uniqueId\":null,\"name\":\"name1\",\"address\":null,\"location\":null}";
		String message = "Success";

		when(farmService.registerWindMill(any(WindMillRequest.class))).thenReturn(message);
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/api/windmill/register").content(request)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andExpect(content().string(equalTo(message)));
	}

	@Test
	public void test_windMillRegister_withInvalidInput() throws Exception {
		String request = "{\"uniqueId\":null,\"name\":\"name1\",\"address\":null,\"location\":null}";
		String errorMessage = "Failure";

		when(farmService.registerWindMill(any(WindMillRequest.class)))
				.thenThrow(new WindMillFarmException(errorMessage));
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/api/windmill/register").content(request)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andExpect(content().string(equalTo(errorMessage)));
	}

	@Test
	public void test_getHistoricalData_withValidInput() throws Exception {
		String uniqueId = "WINDMILL00000001";
		List<WindMillPowerData> powers = new ArrayList<>();
		powers.add(new WindMillPowerData(LocalDate.of(2019, 11, 04), uniqueId, 123456L, 10000.00, 100000, 23456));
		String expectedResponse = "[{\"date\":\"2019-11-04\",\"uniqueId\":\"WINDMILL00000001\",\"sum\":123456,\"avearge\":10000.0,\"maximum\":100000,\"minimum\":23456}]";

		when(farmService.getHistoricalData(eq(uniqueId))).thenReturn(powers);
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/api/windmill/getHistoricalData/" + uniqueId)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().string(equalTo(expectedResponse)));
	}

	@Test
	public void test_getHistoricalData_withInvalidInput() throws Exception {
		String uniqueId = "WINDMILL00000001";
		String errorMessage = "Failure";

		when(farmService.getHistoricalData(eq(uniqueId))).thenThrow(new WindMillFarmException(errorMessage));
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/api/windmill/getHistoricalData/" + uniqueId)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andExpect(content().string(equalTo(errorMessage)));
	}

}
