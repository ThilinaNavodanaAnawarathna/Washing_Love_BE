package com.sliit.washing_love_be.service.impl;

import com.sliit.washing_love_be.client.NumberPlateServiceClient;
import com.sliit.washing_love_be.dto.BookingDto;
import com.sliit.washing_love_be.dto.VehicleDto;
import com.sliit.washing_love_be.service.BookingService;
import com.sliit.washing_love_be.service.NumberPlateService;
import com.sliit.washing_love_be.service.VehicleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;

@Service
@Slf4j
public class NumberPlateServiceImpl implements NumberPlateService {
    private final NumberPlateServiceClient numberPlateServiceClient;
    private final BookingService bookingService;
    private final VehicleService vehicleService;

    @Value("${upload.path}")
    private String uploadDirectory;

    public NumberPlateServiceImpl(NumberPlateServiceClient numberPlateServiceClient, BookingService bookingService, VehicleService vehicleService) {
        this.numberPlateServiceClient = numberPlateServiceClient;
        this.bookingService = bookingService;
        this.vehicleService = vehicleService;
    }

    @Override
    public String checkNumberPlate(MultipartFile numberPlate) throws Exception {
        Path filePath=null;
        String fileName = StringUtils.cleanPath(numberPlate.getOriginalFilename());
        String fileExtension = "";
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0) {
            fileExtension = fileName.substring(dotIndex);
        }
        String newFileName = fileName +  fileExtension;
        filePath = Paths.get(uploadDirectory, newFileName);

        Files.copy(numberPlate.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        String number = numberPlateServiceClient.checkNumberPlate(numberPlate);
//        VehicleDto byVehicleNumber = vehicleService.findByVehicleNumber(number);
//        LocalTime time = LocalTime.now();
//        BookingDto bookingDto = bookingService.checkBookingByTimeAndVehicleId(new Date(), Time.valueOf(time), byVehicleNumber.getId());
//        if(bookingDto == null)
//            throw new RuntimeException("Can't find matching booking");
        return number;
    }
}
