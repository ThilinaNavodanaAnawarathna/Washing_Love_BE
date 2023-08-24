package com.sliit.washing_love_be.dto;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DamageDetectResponse {
    private String[] damageParts;
    private String imageBase64;
}
