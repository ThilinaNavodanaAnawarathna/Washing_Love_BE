package com.sliit.washing_love_be.service.impl;

import com.sliit.washing_love_be.client.NumberPlateServiceClient;
import com.sliit.washing_love_be.dto.BookingDto;
import com.sliit.washing_love_be.dto.VehicleDto;
import com.sliit.washing_love_be.service.BookingService;
import com.sliit.washing_love_be.service.NumberPlateService;
import com.sliit.washing_love_be.service.VehicleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;

@Service
@Slf4j
public class NumberPlateServiceImpl implements NumberPlateService {
    private final NumberPlateServiceClient numberPlateServiceClient;
    private final BookingService bookingService;
    private final VehicleService vehicleService;

    public NumberPlateServiceImpl(NumberPlateServiceClient numberPlateServiceClient, BookingService bookingService, VehicleService vehicleService) {
        this.numberPlateServiceClient = numberPlateServiceClient;
        this.bookingService = bookingService;
        this.vehicleService = vehicleService;
    }

    @Override
    public BookingDto checkNumberPlate(MultipartFile numberPlate) throws Exception {
        String number = numberPlateServiceClient.checkNumberPlate(numberPlate);
        VehicleDto byVehicleNumber = vehicleService.findByVehicleNumber(number);
        LocalTime time = LocalTime.now();
        BookingDto bookingDto = bookingService.checkBookingByTimeAndVehicleId(new Date(), Time.valueOf(time), byVehicleNumber.getId());
        if(bookingDto == null)
            throw new RuntimeException("Can't find matching booking");
        return bookingDto;
    }
}
