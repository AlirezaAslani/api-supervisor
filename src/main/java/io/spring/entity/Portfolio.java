package io.spring.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Portfolio {
    private String portfolioDate;
    private long bimCode;
    private String isin;
    private int quantity;
    private int netAverage;

    public Portfolio() {
    }
}
