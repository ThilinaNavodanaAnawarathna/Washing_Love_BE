package com.sliit.washing_love_be.dto;

import com.sliit.washing_love_be.enumz.InStock;
import com.sliit.washing_love_be.enumz.VehicleBrand;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SparePartDto {
    private Long id;
    private VehicleBrand brand;
    private String model;
    private String partName;
    private int year;
    private double price;
    private InStock inStock;
}
