package com.sliit.washing_love_be.dto;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleBasicDetails {
    private Long id;
    private String vehicleNumber;
}
