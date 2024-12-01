package io.spring.controller;

import java.io.IOException;
import io.prometheus.client.CollectorRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import io.spring.service.ExternalCallService;
import jakarta.servlet.http.HttpServletResponse;
import io.prometheus.client.exporter.common.TextFormat;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The {@code IndexController} class acts as a REST controller that provides
 * an endpoint to expose Prometheus metrics and trigger external service calls.
 *
 * <h3>Core Responsibilities:</h3>
 * <ul>
 *     <li>Invokes the {@link ExternalCallService} to fetch and process external data.</li>
 *     <li>Exposes Prometheus metrics in a format compatible with Prometheus scraping.</li>
 * </ul>
 *
 * <h3>Endpoints:</h3>
 * <ul>
 *     <li><strong>GET /prometheus/metrics</strong>: Triggers external data fetching and
 *     exposes Prometheus metrics in the OpenMetrics format.</li>
 * </ul>
 *
 * @author Alireza Aslani
 * @version 1.0
 * @since 30 Nov 2024
 */
@RestController
public class IndexController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private ExternalCallService externalCallService;

    @Autowired
    public IndexController(ExternalCallService externalCallService) {
        this.externalCallService = externalCallService;
    }

    @PostMapping("/prometheus/metrics")
    public void getMetrics(HttpServletResponse response){

        logger.info("PostMapping /prometheus/metrics");

        externalCallService.getExternalContent();
        response.setContentType(TextFormat.CONTENT_TYPE_004);
        try {
            TextFormat.write004(response.getWriter(), CollectorRegistry.defaultRegistry.metricFamilySamples());
        } catch (IOException e) {
            logger.warn("IOException happens: GetMapping /prometheus/metrics _" +e.getMessage());
            throw new RuntimeException(e);
        }
    }

}

