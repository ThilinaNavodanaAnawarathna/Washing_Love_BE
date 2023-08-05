package com.sliit.washing_love_be.service.impl;

import com.sliit.washing_love_be.client.DamageDetectionServiceClient;
import com.sliit.washing_love_be.dto.VehicleDto;
import com.sliit.washing_love_be.entity.Booking;
import com.sliit.washing_love_be.service.BookingService;
import com.sliit.washing_love_be.service.DamageDetectionService;
import com.sliit.washing_love_be.service.SparePartService;
import com.sliit.washing_love_be.service.VehicleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class DamageDetectionServiceImpl implements DamageDetectionService {
    private final BookingService bookingService;
    private final SparePartService sparePartService;
    private final VehicleService vehicleService;
    private final DamageDetectionServiceClient damageDetectionServiceClient;

    public DamageDetectionServiceImpl(BookingService bookingService, SparePartService sparePartService, VehicleService vehicleService, DamageDetectionServiceClient damageDetectionServiceClient) {
        this.bookingService = bookingService;
        this.sparePartService = sparePartService;
        this.vehicleService = vehicleService;
        this.damageDetectionServiceClient = damageDetectionServiceClient;
    }

    @Override
    public Object detectDamages(MultipartFile front, MultipartFile back, MultipartFile left, MultipartFile right, Long bookingId) throws Exception {
        Booking booking = bookingService.findBookingById(bookingId);
        if (booking == null)
            throw new RuntimeException("Invalid booking details");
        VehicleDto vehicleDto = vehicleService.findById(booking.getVehicle().getId());
        if (vehicleDto == null)
            throw new RuntimeException("Invalid vehicle details");
        String frontReport = damageDetectionServiceClient.checkDamages(front);
        String backReport = damageDetectionServiceClient.checkDamages(back);
        String leftReport = damageDetectionServiceClient.checkDamages(left);
        String rightReport = damageDetectionServiceClient.checkDamages(right);
        return "Test report : " + frontReport + backReport + leftReport + rightReport;
    }
}
