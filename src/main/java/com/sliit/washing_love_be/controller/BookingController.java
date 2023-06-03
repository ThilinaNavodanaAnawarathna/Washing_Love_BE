package com.sliit.washing_love_be.controller;

import com.sliit.washing_love_be.dto.BookingDto;
import com.sliit.washing_love_be.dto.CheckAvailability;
import com.sliit.washing_love_be.enumz.BookingStatus;
import com.sliit.washing_love_be.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<?> saveBooking(@RequestBody BookingDto bookingDto) {
        try {
            BookingDto save = bookingService.save(bookingDto);
            return new ResponseEntity<>(save, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/status")
    public ResponseEntity<?> updateBookingStatus(@RequestParam Long bookingId, @RequestParam BookingStatus bookingStatus) {
        try {
            BookingDto save = bookingService.updateBookingStatus(bookingId, bookingStatus);
            return new ResponseEntity<>(save, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllBookings() {
        try {
            List<BookingDto> allBooking = bookingService.getAllBooking();
            return new ResponseEntity<>(allBooking, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user")
    public ResponseEntity<?> findAllBookingsByUserId(@RequestParam Long userId) {
        try {
            List<BookingDto> allBooking = bookingService.findAllBookingByUserId(userId);
            return new ResponseEntity<>(allBooking, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/availability")
    public ResponseEntity<?> checkAvailability(@RequestBody CheckAvailability checkAvailability) {
        try {
            boolean availability = bookingService.checkAvailability(checkAvailability.getDate()
                    , checkAvailability.getTime());
            return new ResponseEntity<>(availability, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
