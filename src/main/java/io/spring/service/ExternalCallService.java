package io.spring.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import io.spring.entity.ApiResponse;
import io.spring.entity.Portfolio;
import io.spring.utils.TextEncoderDecoder;

import static io.spring.prometheus.Metrics.*;


/**
 * The {@code ExternalCallService} class provides functionality to interact with external APIs,
 * process their responses, and track application metrics using Prometheus.
 * It uses Spring's {@link RestTemplate} to perform HTTP GET requests, processes JSON responses,
 * and updates Prometheus counters and gauges based on the results.
 *
 * <p>This service supports API calls with authentication tokens and handles multiple BIM codes
 * for processing specific resources.</p>
 *
 * <h3>Core Features:</h3>
 * <ul>
 *     <li>Performs API calls to external services based on configurable endpoints.</li>
 *     <li>Parses JSON responses into domain-specific objects.</li>
 *     <li>Tracks metrics for request counts, errors, and specific business logic scenarios.</li>
 *     <li>Handles exceptions such as resource access issues and JSON parsing errors.</li>
 * </ul>
 *
 * <h3>Dependencies:</h3>
 * <ul>
 *     <li>{@link RestTemplate} for HTTP communication.</li>
 *     <li>{@link ObjectMapper} for parsing JSON responses.</li>
 *     <li>{@link TextEncoderDecoder} for decoding token and BIM code values.</li>
 *     <li>Prometheus metrics for tracking application performance and state.</li>
 * </ul>
 *
 * <h3>Metrics Tracked:</h3>
 * <ul>
 *     <li>Total HTTP requests.</li>
 *     <li>Not active responses.</li>
 *     <li>JSON parser errors.</li>
 *     <li>No result responses.</li>
 *     <li>Business logic scenarios involving zero quantities or zero net averages.</li>
 *     <li>Number of BIM codes processed.</li>
 * </ul>
 *
 * @author Alireza Aslani
 * @version 1.0
 * @since 23 Nov 2024
 */
@Service
public class ExternalCallService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${token}")
    private String token;

    @Value("${id}")
    private String id;

    @Value("${endpoint}")
    private String endpoint;

    private RestTemplate restTemplate;

    @Autowired
    public ExternalCallService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void getExternalContent() {

        requestCounter();

        ApiResponse apiResponse = null;

        // Create headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", TextEncoderDecoder.decode(token));
        logger.info("set token");

        String[] bimcodes = TextEncoderDecoder.decode(id).split(",");


        // Create an HttpEntity with the headers
        HttpEntity<String> entity = new HttpEntity<>(headers);

        for(String bimCode:bimcodes){

            countOfBimCode();

            try {
                logger.info("calling restTemplate");
                ResponseEntity<String> response = restTemplate.exchange(
                        endpoint+bimCode,
                        HttpMethod.GET,
                        entity,
                        String.class
                );

                // Extract the response body
                String jsonString = response.getBody();


                // Parse the JSON string into an object
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    logger.info("map response into the API Response Class");
                    apiResponse = objectMapper.readValue(jsonString, ApiResponse.class);
                    apiResponseChecker(apiResponse);

                } catch (Exception e) {
                    logger.warn("exception happens map response into the API Response Class");
                    jsonParserErrorCounter();
                    e.printStackTrace();

                }
            }catch (ResourceAccessException resourceAccessException){
                requestNotActivateCounter();
            }
        }


    }

    private void requestCounter() {
        requestCounter.inc();
    }

    private void requestNotActivateCounter(){
        logger.info("service not activate");
        requestNotActivateCounter.inc();
    }

    private void jsonParserErrorCounter(){
        logger.info("Error in JSON Parser");
        jsonParser.inc();
    }

    private void noResultCounter(){
        logger.info("No result");
        requestNoResultCounter.inc();
    }

    private void hasQuantity_ZeroNetAverage(){
        logger.info("record has quantity by zero net average");
        hasQuantityZeroNetAverage.inc();
    }

    private void hasNetAverage_ZeroQuantity(){
        logger.info("record has net average by zero quantity");
        hasNetAverageZeroQuantity.inc();

    }

    private void countOfBimCode(){

        numberOfBimCode.inc();
    }


    private void apiResponseChecker(ApiResponse apiResponse){

        if(apiResponse.getData().isEmpty())
            noResultCounter();
        for(Portfolio portfolio:apiResponse.getData()){
            if(portfolio.getQuantity() == 0 && portfolio.getNetAverage() > 0)
                hasNetAverage_ZeroQuantity();
            if(portfolio.getQuantity()>0 && portfolio.getNetAverage() == 0)
                hasQuantity_ZeroNetAverage();
        }
    }

}
