package com.assignment.spring;

import static org.assertj.core.api.Assertions.assertThat;

import com.assignment.spring.dao.WeatherEntity;
import com.assignment.spring.rest.WeatherController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTests {
	@Autowired
	private WeatherController controller;

	@Autowired
	MockMvc mvc;

	@Test
	public void contextLoads() {
		assertThat(controller).isNotNull();
	}

	@Test
	public void badRequestTest() throws Exception {
		mvc.perform(get("/weather"))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void cacheTest() throws Exception {
		// shouldn't be present in the cache in the beginning
		WeatherEntity weatherInAmsterdam = controller.findBySearchedCity("Amsterdam");
		assertThat(weatherInAmsterdam).isNull();

		mvc.perform(get("/weather?city=Amsterdam"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.city", is("Amsterdam")));

		// should be present in the cache now
		weatherInAmsterdam = controller.findBySearchedCity("Amsterdam");
		assertThat(weatherInAmsterdam).isNotNull();

		int databaseId = weatherInAmsterdam.getId();

		// The id shouldn't increase
		mvc.perform(get("/weather?city=Amsterdam"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(databaseId)))
				.andExpect(jsonPath("$.city", is("Amsterdam")));
	}
}
