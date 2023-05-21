package com.sliit.washing_love_be.entity;

import com.sliit.washing_love_be.dto.UserDto;
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
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @Enumerated(EnumType.STRING)
    @Column(name = "brand", nullable = false)
    private VehicleBrand brand;
    @Column(name = "model", nullable = false, length = 100)
    private String model;
    @Column(name = "year", nullable = false)
    private int year;
}
