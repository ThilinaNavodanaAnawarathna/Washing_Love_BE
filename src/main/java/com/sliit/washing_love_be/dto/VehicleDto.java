package com.sliit.washing_love_be.dto;

import com.sliit.washing_love_be.enumz.VehicleBrand;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDto {
    private Long id;
    private UserBasicDetails user;
    private VehicleBrand brand;
    private String model;
    private String vehicleNumber;
    private int year;
}
