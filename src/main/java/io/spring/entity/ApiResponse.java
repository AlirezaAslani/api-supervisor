package io.spring.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponse {

    private List<Portfolio> data;

    private String message;

    private String status;


    public ApiResponse() {
    }
}
