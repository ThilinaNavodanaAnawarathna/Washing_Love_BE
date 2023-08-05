package com.sliit.washing_love_be.controller;

import com.sliit.washing_love_be.dto.BookingDto;
import com.sliit.washing_love_be.dto.GateDto;
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

    public SystemController(NumberPlateService numberPlateService, GateService gateService, DamageDetectionService damageDetectionService) {
        this.numberPlateService = numberPlateService;
        this.gateService = gateService;
        this.damageDetectionService = damageDetectionService;
    }

    @PostMapping("/numberPlate")
    public ResponseEntity<?> checkNumberPlate(@RequestParam MultipartFile numberPlate) throws Exception {
        BookingDto bookingDto = numberPlateService.checkNumberPlate(numberPlate);
        if (bookingDto == null)
            return new ResponseEntity<>("Can't find booking details", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(bookingDto, HttpStatus.OK);
    }

    @PostMapping("/gate")
    public ResponseEntity<?> gateOpening(@RequestBody GateDto gateDto) throws Exception {
        String operation = gateService.gateOperation(gateDto);
        if (operation != "Success")
            return new ResponseEntity<>("Can't open gate", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(operation, HttpStatus.OK);
    }

    @PostMapping("/damage")
    public ResponseEntity<?> gateOpening(@RequestParam MultipartFile front, @RequestParam MultipartFile back,
                                         @RequestParam MultipartFile left, @RequestParam MultipartFile right,
                                         @RequestParam Long bookingId)
            throws Exception {
        Object result = damageDetectionService.detectDamages(front, back, left, right, bookingId);
        if (result != null)
            return new ResponseEntity<>("Can't detect damages", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}

