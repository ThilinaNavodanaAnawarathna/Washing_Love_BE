package com.sliit.washing_love_be.client;

import com.google.gson.Gson;
import com.sliit.washing_love_be.config.security.WebClientConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class NumberPlateServiceClient {
    private static final String CONTENT_TYPE = "application/json ";
    private final WebClientConfiguration webClientConfiguration;
    private final boolean isMock = true;

    public String checkNumberPlate(MultipartFile numberPlate) {
        if (!isMock) {
            return webClientConfiguration.webClientNumberPlateService().post()
                    .uri(uriBuilder -> uriBuilder
                            .path("/validate")
                            .build())
                    .headers(
                            httpHeaders -> {
                                httpHeaders.set(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE);
                            })

                    .body(Mono.just(new Gson().toJson(numberPlate)), String.class)
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, response -> response.bodyToMono(String.class)
                            .flatMap(body -> Mono.error(new RuntimeException(new JSONObject(body).get("message").toString()))))
                    .onStatus(HttpStatus::is5xxServerError, response -> response.bodyToMono(String.class)
                            .flatMap(body -> Mono.error(new RuntimeException(new JSONObject(body).get("message").toString()))))
                    .bodyToMono(String.class)
                    .block();
        } else {
            String fileName = StringUtils.cleanPath(numberPlate.getOriginalFilename());
            return fileName.split("\\.")[0] ;
        }
    }
}
