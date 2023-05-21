package com.sliit.washing_love_be.service;

import com.sliit.washing_love_be.dto.VehicleDto;

import java.util.List;

public interface VehicleService {
    VehicleDto save(VehicleDto vehicleDto)throws Exception;
    VehicleDto update(VehicleDto vehicleDto)throws Exception;
    boolean delete(Long vehicleId)throws Exception;
    VehicleDto findById(Long vehicleId)throws Exception;
    List<VehicleDto> findByUserId(Long userId)throws Exception;

}
