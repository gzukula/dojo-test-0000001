
package com.assignment.spring.api;

import lombok.Data;

@Data
public class Wind extends ApiObject {
    private Double speed;
    private Integer deg;
    private Integer gust; // meters/second not clear enough and contradicting information in API description
}
