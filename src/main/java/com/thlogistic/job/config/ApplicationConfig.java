package com.thlogistic.job.config;

import com.thlogistic.job.client.auth.AuthorizationClient;
import com.thlogistic.job.client.product.ProductClient;
import com.thlogistic.job.client.route.RouteClient;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    private static String domainUrl = System.getenv("DOMAIN_URL");

    public static final String AUTHORIZATION_BASE_URL = "http://" + "www.thinhlh.com" + ":8000";
    public static final String PRODUCT_BASE_URL = "http://" + "www.thinhlh.com" + ":8080";
    public static final String ROUTE_BASE_URL = "http://" + "www.thinhlh.com" + ":8083";

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

}
