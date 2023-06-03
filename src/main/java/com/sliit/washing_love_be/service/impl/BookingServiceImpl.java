package com.sliit.washing_love_be.service.impl;

import com.sliit.washing_love_be.dto.BookingDto;
import com.sliit.washing_love_be.entity.Booking;
import com.sliit.washing_love_be.entity.User;
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
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class BookingServiceImpl implements BookingService {
    private final static SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd");
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
            List<Booking> bookingByTimeAndDate = bookingRepository.searchBookingByTimeAndDate(
                    BookingStatus.PENDING.name()
                    , format.format(booking.getDate())
                    , booking.getStartTime());
            if (bookingByTimeAndDate != null && !bookingByTimeAndDate.isEmpty())
                throw new Exception("Booking time already exists");
            Booking save = bookingRepository.save(booking);
            return modelMapper.map(save, BookingDto.class);
        } catch (Exception e) {
            log.error("BookingServiceImpl : Can't save vehicle | Error : {}", e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public boolean checkAvailability(Date date, Time startTime) throws Exception {
        try {
            List<Booking> bookingByTimeAndDate = bookingRepository.searchBookingByTimeAndDate(
                    BookingStatus.PENDING.name()
                    , format.format(date)
                    , startTime);
            return bookingByTimeAndDate == null || bookingByTimeAndDate.isEmpty();
        } catch (Exception e) {
            log.error("BookingServiceImpl : Can't check Availability | Error : {}", e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public BookingDto updateBookingStatus(Long bookingId, BookingStatus bookingStatus) throws Exception {
        try {
            Optional<Booking> booking = bookingRepository.findById(bookingId);
            if (!booking.isPresent())
                throw new Exception("Can't find matching booking details");
            Booking bookingModel = booking.get();
            bookingModel.setBookingStatus(bookingStatus);
            return modelMapper.map(bookingRepository.save(bookingModel), BookingDto.class);
        } catch (Exception e) {
            log.error("BookingServiceImpl : Can't update Booking Status | Error : {}", e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<BookingDto> findAllBookingByUserId(Long userId) throws Exception {
        try {
            Optional<User> user = userRepository.findById(userId);
            if (!user.isPresent())
                throw new Exception("Can't find matching user details");
            return bookingRepository.findAllByUser(user.get()).stream()
                    .map(element -> modelMapper.map(element, BookingDto.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("BookingServiceImpl : Can't find All Booking By UserId | Error : {}", e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<BookingDto> getAllBooking() throws Exception {
        try {
            return bookingRepository.findAll().stream()
                    .map(element -> modelMapper.map(element, BookingDto.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("BookingServiceImpl : Can't get All Bookings | Error : {}", e.getMessage());
            throw new Exception(e.getMessage());
        }
    }
}
