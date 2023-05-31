package com.sliit.washing_love_be.service.impl;

import com.sliit.washing_love_be.dto.BookingDto;
import com.sliit.washing_love_be.entity.Booking;
import com.sliit.washing_love_be.entity.Vehicle;
import com.sliit.washing_love_be.enumz.BookingStatus;
import com.sliit.washing_love_be.repository.BookingRepository;
import com.sliit.washing_love_be.repository.UserRepository;
import com.sliit.washing_love_be.repository.VehicleRepository;
import com.sliit.washing_love_be.service.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, ModelMapper modelMapper, UserRepository userRepository, VehicleRepository vehicleRepository) {
        this.bookingRepository = bookingRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public BookingDto save(BookingDto bookingDto) throws Exception {
        try {
            Optional<Vehicle> vehicle = vehicleRepository.findById(bookingDto.getVehicle().getId());
            LocalTime localTime = bookingDto.getStartTime().toLocalTime().plusMinutes(30);
            bookingDto.setEndTime(Time.valueOf(localTime));
            if (!vehicle.isPresent())
                throw new Exception("Can't find vehicle details");
            if (vehicle.get().getUser().getId() != bookingDto.getUser().getId())
                throw new Exception("Vehicle details are incorrect");
            Booking booking = modelMapper.map(bookingDto, Booking.class);
            System.out.println(booking.getStartTime());
            System.out.println(booking.getEndTime());
            System.out.println(booking.getDate());
            List<Booking> bookingByTimeAndDate = bookingRepository.searchBookingByTimeAndDate(booking.getDate()
                    , booking.getStartTime()
                    , booking.getEndTime()
                    , BookingStatus.PENDING);
            if (bookingByTimeAndDate != null && !bookingByTimeAndDate.isEmpty())
                throw new Exception("Booking time already exists");
            Booking save = bookingRepository.save(booking);
            return modelMapper.map(save, BookingDto.class);

        } catch (Exception e) {
            log.error("VehicleServiceImpl : Can't save vehicle | Error : {}", e.getMessage());
            throw new Exception(e.getMessage());
        }
    }
}
