package com.sliit.washing_love_be.entity;

import com.sliit.washing_love_be.enumz.InStock;
import com.sliit.washing_love_be.enumz.VehicleBrand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SparePart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "brand", nullable = false)
    private VehicleBrand brand;
    @Column(name = "model", nullable = false, length = 100)
    private String model;
    @Column(name = "partName", nullable = false, length = 100)
    private String partName;
    @Column(name = "year", nullable = false)
    private int year;
    @Column(name = "price", nullable = false, length = 100)
    private double price;
    @Enumerated(EnumType.STRING)
    @Column(name = "inStock", nullable = false)
    private InStock inStock;
}
