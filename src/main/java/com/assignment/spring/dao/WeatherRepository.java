package com.assignment.spring.dao;

import com.assignment.spring.dao.WeatherEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WeatherRepository extends CrudRepository<WeatherEntity, Integer> {
    List<WeatherEntity> findBySearchedCity(String city);
}
