package com.sliit.washing_love_be.dto;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Results {
//    private Integer[] boxes;
    private Double[] confidences;
    private String[] classes;
}
