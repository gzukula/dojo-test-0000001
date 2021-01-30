package com.assignment.spring.rest;

import com.assignment.spring.api.WeatherResponse;
import com.assignment.spring.dao.WeatherEntity;
import com.assignment.spring.dao.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@RestController
public class WeatherController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WeatherRepository weatherRepository;

    @Value("${weather-api.id}")
    private String weatherApiId;

    @Value("${weather-api.url}")
    private String weatherApiUrl;

    @Value("${coding-dojo.cache-data}")
    private Integer cacheDurationSeconds;

    @RequestMapping("/weather")
    public WeatherEntity weather(HttpServletRequest request) throws ResponseStatusException {
        String city = request.getParameter("city");

        if (city == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "<city> argument is missing");
        }

        WeatherEntity cachedEntity = findBySearchedCity(city);
        long epochSecondsNow = Instant.now().toEpochMilli() / 1000;

        if ((cachedEntity != null) &&
            ((epochSecondsNow - cachedEntity.getCreatedAt()) < cacheDurationSeconds)) {
            return cachedEntity;
        }

        String url = weatherApiUrl.replace("{city}", city).replace("{appid}", weatherApiId);

        WeatherEntity weatherInCity = toEntity(restTemplate.getForEntity(url, WeatherResponse.class).getBody(), cachedEntity);
        weatherInCity.setSearchedCity(city);
        weatherInCity.setCreatedAt(epochSecondsNow);
        weatherRepository.save(weatherInCity);

        return weatherInCity;
    }

    public WeatherEntity findBySearchedCity(String city) {
        return weatherRepository
                .findBySearchedCity(city)
                .stream()
                .findFirst()
                .orElse(null);
    }

    private WeatherEntity toEntity(WeatherResponse response, WeatherEntity cachedEntity) {
        WeatherEntity entity = cachedEntity;
        if (entity == null) {
            entity = new WeatherEntity();
        }

        entity.setCity(response.getName());
        entity.setCountry(response.getSys().getCountry());
        entity.setTemperature(response.getMain().getTemp());
        return entity;
    }
}
