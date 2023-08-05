package com.sliit.washing_love_be.client;

import com.sliit.washing_love_be.config.security.WebClientConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class GateServiceClient {
    private final WebClientConfiguration webClientConfiguration;
    private final boolean isMock = true;

    public String gateOperation(int gateNumber,String operation) {
        log.info("GateServiceClient | gateOperation | Open/Cole Gates");
        if (!isMock) {

            return webClientConfiguration.webClientGateService().get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/gate")
                            .build())
                    .headers(
                            httpHeaders -> {
                                httpHeaders.set("gate-no", String.valueOf(gateNumber));
                            })
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, response -> response.bodyToMono(String.class)
                            .flatMap(body -> Mono.error(new RuntimeException(new JSONObject(body).get("message").toString()))))
                    .onStatus(HttpStatus::is5xxServerError, response -> response.bodyToMono(String.class)
                            .flatMap(body -> Mono.error(new RuntimeException(new JSONObject(body).get("message").toString()))))
                    .bodyToMono(String.class)
                    .block();
        } else {
            return "Success";
        }

    }
}
