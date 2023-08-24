package com.sliit.washing_love_be.controller;

import com.sliit.washing_love_be.dto.BookingDto;
import com.sliit.washing_love_be.dto.GateDto;
import com.sliit.washing_love_be.service.BookingService;
import com.sliit.washing_love_be.service.DamageDetectionService;
import com.sliit.washing_love_be.service.GateService;
import com.sliit.washing_love_be.service.NumberPlateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/system")
public class SystemController {
    private final NumberPlateService numberPlateService;
    private final GateService gateService;
    private final DamageDetectionService damageDetectionService;
    private final BookingService bookingService;

    public SystemController(NumberPlateService numberPlateService, GateService gateService, DamageDetectionService damageDetectionService, BookingService bookingService) {
        this.numberPlateService = numberPlateService;
        this.gateService = gateService;
        this.damageDetectionService = damageDetectionService;
        this.bookingService = bookingService;
    }

    @PostMapping("/numberPlate")
    public ResponseEntity<?> checkNumberPlate(@RequestParam("numberPlate") MultipartFile numberPlate) throws Exception {
        String number = numberPlateService.checkNumberPlate(numberPlate);
        if (number == null)
            return new ResponseEntity<>("Can't read number plate", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(number, HttpStatus.OK);
    }

    @PostMapping("/gate")
    public ResponseEntity<?> gateOpening(@RequestBody GateDto gateDto) throws Exception {
        String operation = gateService.gateOperation(gateDto);
        if (operation != "Success")
            return new ResponseEntity<>("Can't open gate", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(operation, HttpStatus.OK);
    }

    @PostMapping("/damage")
    public ResponseEntity<?> gateOpening(@RequestParam("front") MultipartFile front, @RequestParam("back") MultipartFile back,
                                         @RequestParam("left") MultipartFile left, @RequestParam("right") MultipartFile right,
                                         @RequestParam Long bookingId)
            throws Exception {
        Object result = damageDetectionService.detectDamages(front, back, left, right, bookingId);
        if (result == null)
            return new ResponseEntity<>("Can't detect damages", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/validateBooking")
    public ResponseEntity<?> validateBooking(@RequestParam String vehicleNumber) throws Exception {
        BookingDto bookingDto = bookingService.validateBooking(vehicleNumber);
        if (bookingDto == null)
            return new ResponseEntity<>("Can't read number plate", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(bookingDto, HttpStatus.OK);
    }

}

