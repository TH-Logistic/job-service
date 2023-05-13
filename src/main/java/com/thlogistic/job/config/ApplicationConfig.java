package com.thlogistic.job.config;

import com.thlogistic.job.client.auth.AuthorizationClient;
import com.thlogistic.job.client.healthcheck.HealthcheckClient;
import com.thlogistic.job.client.product.ProductClient;
import com.thlogistic.job.client.route.RouteClient;
import com.thlogistic.job.client.transportation.TransportationClient;
import com.thlogistic.job.client.user.UserClient;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    // TODO: Change this into env var
    private static String domainUrl = System.getenv("DOMAIN_URL");

    public static final String AUTHORIZATION_BASE_URL = "http://" + "www.thinhlh.com" + ":8000";
    public static final String PRODUCT_BASE_URL = "http://" + "www.thinhlh.com" + ":8080";
    public static final String ROUTE_BASE_URL = "http://" + "www.thinhlh.com" + ":8083";
    public static final String TRANSPORTATION_BASE_URL = "http://" + "www.thinhlh.com" + ":8081";
    public static final String HEALTHCHECK_BASE_URL = "http://" + "www.thinhlh.com" + ":8084";
    public static final String USER_BASE_URL = "http://" + "www.thinhlh.com" + ":8001";

    @Bean
    public AuthorizationClient authorizationClient() {
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .target(AuthorizationClient.class, AUTHORIZATION_BASE_URL);
    }

    @Bean
    public ProductClient productClient() {
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .target(ProductClient.class, PRODUCT_BASE_URL);
    }

    @Bean
    public RouteClient routeClient() {
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .target(RouteClient.class, ROUTE_BASE_URL);
    }

    @Bean
    public TransportationClient transportationClient() {
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .target(TransportationClient.class, TRANSPORTATION_BASE_URL);
    }

    @Bean
    public UserClient userClient() {
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .target(UserClient.class, USER_BASE_URL);
    }

    @Bean
    public HealthcheckClient healthcheckClient() {
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .target(HealthcheckClient.class, HEALTHCHECK_BASE_URL);
    }

}
