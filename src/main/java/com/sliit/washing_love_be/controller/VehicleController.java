package com.sliit.washing_love_be.controller;

import com.sliit.washing_love_be.dto.VehicleDto;
import com.sliit.washing_love_be.service.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicle")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public ResponseEntity<?> addVehicle(@RequestBody VehicleDto vehicleDto) throws Exception{
        VehicleDto save = vehicleService.save(vehicleDto);
        if (save==null)
            return new ResponseEntity<>("Can't add vehicle", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(save,HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<?> updateVehicle(@RequestBody VehicleDto vehicleDto)throws Exception{
        VehicleDto update = vehicleService.update(vehicleDto);
        if (update==null)
            return new ResponseEntity<>("Can't update vehicle", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(update,HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteVehicle(@RequestParam Long vehicleId)throws Exception{
        boolean delete = vehicleService.delete(vehicleId);
        if (!delete)
            return new ResponseEntity<>("Can't delete vehicle", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>("Delete success",HttpStatus.OK);
    }

    @GetMapping("vehicle")
    public ResponseEntity<?> findById(@RequestParam Long vehicleId)throws Exception{
        VehicleDto vehicleDto = vehicleService.findById(vehicleId);
        if (vehicleDto==null)
            return new ResponseEntity<>("Can't find vehicle", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(vehicleDto,HttpStatus.OK);
    }

    @GetMapping("user")
    public ResponseEntity<?> findByUserId(@RequestParam Long userId)throws Exception{
        List<VehicleDto> vehicleDtos = vehicleService.findByUserId(userId);
        if (vehicleDtos==null)
            return new ResponseEntity<>("Can't find vehicles", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(vehicleDtos,HttpStatus.OK);
    }
}
