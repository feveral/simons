package com.memery.simons.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;

@Configuration
public class HttpClientConfiguration {
    @Bean
    public HttpClient getHttpClient() {
        return HttpClient.newBuilder().build();
    }
}
