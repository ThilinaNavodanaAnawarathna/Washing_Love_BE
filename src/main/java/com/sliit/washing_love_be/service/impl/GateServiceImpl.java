package com.sliit.washing_love_be.service.impl;

import com.sliit.washing_love_be.client.GateServiceClient;
import com.sliit.washing_love_be.dto.BookingDto;
import com.sliit.washing_love_be.dto.GateDto;
import com.sliit.washing_love_be.enumz.BookingStatus;
import com.sliit.washing_love_be.service.BookingService;
import com.sliit.washing_love_be.service.GateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GateServiceImpl implements GateService {
    private final GateServiceClient gateServiceClient;
    private final BookingService bookingService;

    public GateServiceImpl(GateServiceClient gateServiceClient, BookingService bookingService) {
        this.gateServiceClient = gateServiceClient;
        this.bookingService = bookingService;
    }

    @Override
    public String gateOperation(GateDto gateDto) throws Exception {
        if (gateDto.isGate1()) {
            BookingDto bookingDto = bookingService.updateBookingStatus(gateDto.getBookingId(), BookingStatus.ONGOING);
            if (bookingDto == null)
                throw new RuntimeException("Can't open gate. Booking details incorrect");
            String operation = gateServiceClient.gateOperation(1,"open");
            String operation2 = gateServiceClient.gateOperation(2,"close");
            return operation2;
        } else if (gateDto.isGate2()) {
            BookingDto bookingDto = bookingService.updateBookingStatus(gateDto.getBookingId(), BookingStatus.COMPLETED);
            if (bookingDto == null)
                throw new RuntimeException("Can't open gate. Booking details incorrect");
            String operation = gateServiceClient.gateOperation(1,"close");
            String operation2 = gateServiceClient.gateOperation(2,"open");
            return operation2;
        } else {
            gateServiceClient.gateOperation(1,gateDto.isGate1()?"open":"close");
            return gateServiceClient.gateOperation(2,gateDto.isGate2()?"open":"close");
        }

    }
}
