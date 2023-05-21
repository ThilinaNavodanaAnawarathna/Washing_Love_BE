package com.sliit.washing_love_be.service.impl;

import com.sliit.washing_love_be.dto.VehicleDto;
import com.sliit.washing_love_be.entity.User;
import com.sliit.washing_love_be.entity.Vehicle;
import com.sliit.washing_love_be.repository.UserRepository;
import com.sliit.washing_love_be.repository.VehicleRepository;
import com.sliit.washing_love_be.service.VehicleService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VehicleServiceImpl implements VehicleService {
    private final VehicleRepository vehicleRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.vehicleRepository = vehicleRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public VehicleDto save(VehicleDto vehicleDto) throws Exception {
        try {
            Vehicle vehicle = modelMapper.map(vehicleDto, Vehicle.class);
            vehicle.setBrand(vehicleDto.getBrand());
            Vehicle save = vehicleRepository.save(vehicle);
            return modelMapper.map(save, VehicleDto.class);
        } catch (Exception e) {
            log.error("VehicleServiceImpl : Can't save vehicle | Error : {}", e.getMessage());
            throw new Exception("Can't save vehicle details");
        }

    }

    @Override
    public VehicleDto update(VehicleDto vehicleDto) throws Exception {
        try {
            Optional<Vehicle> byId = vehicleRepository.findById(vehicleDto.getId());
            if (!byId.isPresent())
                throw new Exception("Can't find vehicle details");
            Vehicle vehicle = byId.get();
            Vehicle update = vehicleRepository.save(Vehicle.builder().id(vehicle.getId())
                    .model(vehicleDto.getModel())
                    .brand(vehicleDto.getBrand())
                    .year(vehicleDto.getYear())
                    .user(vehicle.getUser()).build());
            return modelMapper.map(update, VehicleDto.class);

        } catch (Exception e) {
            log.error("VehicleServiceImpl : Can't update vehicle | Error : {}", e.getMessage());
            throw new Exception("Can't update vehicle details");
        }
    }

    @Override
    public boolean delete(Long vehicleId) throws Exception {
        try {
            Optional<Vehicle> byId = vehicleRepository.findById(vehicleId);
            if (!byId.isPresent())
                throw new Exception("Can't find vehicle details");
            vehicleRepository.delete(byId.get());
            return true;
        } catch (Exception e) {
            log.error("VehicleServiceImpl : Can't delete vehicle | Error : {}", e.getMessage());
            throw new Exception("Can't delete vehicle details");
        }
    }

    @Override
    public VehicleDto findById(Long vehicleId) throws Exception {
        try {
            Optional<Vehicle> byId = vehicleRepository.findById(vehicleId);
            if (!byId.isPresent())
                throw new Exception("Can't find vehicle details");
            return modelMapper.map(byId.get(), VehicleDto.class);
        } catch (Exception e) {
            log.error("VehicleServiceImpl : Can't find vehicle | Error : {}", e.getMessage());
            throw new Exception("Can't find vehicle details");
        }
    }

    @Override
    public List<VehicleDto> findByUserId(Long userId) throws Exception {
        try {
            Optional<User> user = userRepository.findById(userId);
            if (!user.isPresent())
                throw new Exception("Can't find user");
            List<Vehicle> vehicles = vehicleRepository.findAllByUser(user.get());
           return vehicles.stream()
                    .map(element -> modelMapper.map(element, VehicleDto.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("VehicleServiceImpl : Can't find vehicle | Error : {}", e.getMessage());
            throw new Exception("Can't find vehicle details");
        }
    }
}
