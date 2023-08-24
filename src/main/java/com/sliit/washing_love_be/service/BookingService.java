package com.sliit.washing_love_be.service;

import com.sliit.washing_love_be.dto.BookingDto;
import com.sliit.washing_love_be.entity.Booking;
import com.sliit.washing_love_be.enumz.BookingStatus;

import java.sql.Time;
import java.util.Date;
import java.util.List;


public interface BookingService {
    BookingDto save(BookingDto bookingDto) throws Exception;
    boolean checkAvailability(Date date, Time startTime) throws Exception;
    BookingDto checkBookingByTimeAndVehicleId(Date date, Time startTime, Long vehicleId) throws Exception;
    BookingDto updateBookingStatus(Long bookingId, BookingStatus bookingStatus)throws Exception;
    List<BookingDto> findAllBookingByUserId(Long userId)throws Exception;
    Booking findBookingById(Long bookingId)throws Exception;
    boolean deleteBooking(Long bookingId)throws Exception;
    List<BookingDto> getAllBooking()throws Exception;
    BookingDto validateBooking(String vehicleNumber)throws Exception;
}
