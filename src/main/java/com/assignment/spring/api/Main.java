package com.assignment.spring.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Main extends ApiObject {
    private Double temp;
    private Double feelsLike;
    private Integer pressure;
    private Integer humidity;
    private Double tempMin;
    private Double tempMax;
    private Integer seaLevel;

    @JsonProperty("grnd_level")
    private Integer groundLevel;
}
