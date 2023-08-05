package com.sliit.washing_love_be.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {
    public WebClientConfiguration() {
    }

    @Bean
    public WebClient webClientNumberPlateService() {
        return WebClient.create("localhost:8081//wl/v1/numberPlate")
                .mutate().exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(clientCodecConfigurer -> clientCodecConfigurer.defaultCodecs()
                                .maxInMemorySize(16 * 1024 * 1024)).build())
                .build();
    }

    @Bean
    public WebClient webClientGateService() {
        return WebClient.create("localhost:8081//wl/v1/gate")
                .mutate().exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(clientCodecConfigurer -> clientCodecConfigurer.defaultCodecs()
                                .maxInMemorySize(16 * 1024 * 1024)).build())
                .build();
    }

    @Bean
    public WebClient webClientDamageDetectService() {
        return WebClient.create("localhost:8081//wl/v1/damage")
                .mutate().exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(clientCodecConfigurer -> clientCodecConfigurer.defaultCodecs()
                                .maxInMemorySize(16 * 1024 * 1024)).build())
                .build();
    }
}