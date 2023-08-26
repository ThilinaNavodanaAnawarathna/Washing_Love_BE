package com.sliit.washing_love_be.client;

import com.google.gson.Gson;
import com.sliit.washing_love_be.config.security.WebClientConfiguration;
import com.sliit.washing_love_be.dto.DamageDetectResponse;
import com.sliit.washing_love_be.dto.NumberPlateDetectResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class NumberPlateServiceClient {
    private static final String CONTENT_TYPE = "multipart/form-data";
    private final WebClientConfiguration webClientConfiguration;
    private final boolean isMock = false;

    public NumberPlateDetectResponse checkNumberPlate(MultipartFile numberPlate) {
        if (!isMock) {
            MultipartBodyBuilder builder = new MultipartBodyBuilder();
            builder.part("file", numberPlate.getResource());
            return webClientConfiguration.webClientNumberPlateService().post()
                    .uri(uriBuilder -> uriBuilder
                            .path("/detection")
                            .build())
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData(builder.build()))
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, response -> response.bodyToMono(String.class)
                            .flatMap(body -> Mono.error(new RuntimeException(new JSONObject(body).get("message").toString()))))
                    .onStatus(HttpStatus::is5xxServerError, response -> response.bodyToMono(String.class)
                            .flatMap(body -> Mono.error(new RuntimeException(new JSONObject(body).get("message").toString()))))
                    .bodyToMono(NumberPlateDetectResponse.class)
                    .block();
        } else {
            String fileName = StringUtils.cleanPath(numberPlate.getOriginalFilename());
            NumberPlateDetectResponse response=new NumberPlateDetectResponse();
            response.setNumber(fileName.split("\\.")[0]);
            return response;
        }
    }
}
