package io.spring.prometheus;

import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
/**
 * The {@code Metrics} class defines and registers Prometheus metrics
 * to monitor the application's performance and behavior.
 * It provides counters for cumulative event tracking and gauges for tracking current states.
 *
 * <p>This class utilizes the Prometheus Java client library to create metrics
 * that can be scraped by a Prometheus server for analysis and visualization.</p>
 *
 * <h3>Metrics Overview</h3>
 * <ul>
 *   <li>Counters: Metrics that only increase over time, typically used for event counts.</li>
 *   <li>Gauges: Metrics that can increase or decrease, used to represent current states or values.</li>
 * </ul>
 *
 * @author Alireza Aslani
 * @version 1.0
 * @since 23 Nov 2024
 */
public class Metrics {
    // Define a request Counter
    public static final Counter requestCounter = Counter.build()
            .name("http_requests_total")
            .help("Total HTTP requests")
            .register();

    // Define a request Not Activate Counter
    public static final Counter requestNotActivateCounter = Counter.build()
            .name("not_active_response")
            .help("Total not active response")
            .register();

    // Define a request Not Activate Counter
    public static final Gauge jsonParser = Gauge.build()
            .name("json_parser_error")
            .help("Total JSON parser error")
            .register();

    // Define a request No Result Counter
    public static final Gauge requestNoResultCounter = Gauge.build()
            .name("no_result_counter")
            .help("No result counter")
            .register();

    // Define a request No Result Counter
    public static final Gauge hasQuantityZeroNetAverage = Gauge.build()
            .name("has_quantity_zero_net_average")
            .help("Has Quantity with Zero Net Average")
            .register();


    // Define a request No Result Counter
    public static final Gauge hasNetAverageZeroQuantity = Gauge.build()
            .name("has_net_average_zero_quantity")
            .help("Has Net Average with Zero Quantity")
            .register();

    // Define number of BimCode
    public static final Gauge numberOfBimCode = Gauge.build()
            .name("BimCode_Count")
            .help("Current BimCodes")
            .register();

    // Define a Gauge
    public static final Gauge currentUsers = Gauge.build()
            .name("active_users")
            .help("Current active users")
            .register();
}
