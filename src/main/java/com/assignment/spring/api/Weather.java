
package com.assignment.spring.api;

import lombok.Data;

@Data
public class Weather extends ApiObject {
    private Integer id;
    private String main;
    private String description;
    private String icon;
}
