package com.sliit.washing_love_be.repository;

import com.sliit.washing_love_be.entity.User;
import com.sliit.washing_love_be.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle,Long> {
    List<Vehicle> findAllByUser(User user);
    Optional<Vehicle> findById(Long id);
}
