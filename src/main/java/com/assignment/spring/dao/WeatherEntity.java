package com.assignment.spring.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "weather")
public class WeatherEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @JsonIgnore
    private Long createdAt; // epoch seconds

    // searched city might not match the city in the response
    // (e.g. search for "Utrecht" returns "Provincie Utrecht" as the 'city')
    @JsonIgnore
    private String searchedCity;

    private String city;

    private String country;

    private Double temperature;
}
