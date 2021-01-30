package com.assignment.spring.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class WeatherResponse {
    private Coord coord;
    private List<Weather> weather; // an array
    private String base; // internal parameter
    private Main main;
    private Wind wind;
    private Clouds clouds;

    //private Rain rain; // not implemented
    //private Snow snow; // not implemented

    @JsonProperty("dt")
    private Long date;

    private Sys sys;
    private Long timezone;

    private String id;
    private String name;
    private String cod; // internal parameter
}
