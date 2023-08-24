package com.sliit.washing_love_be.controller;

import com.sliit.washing_love_be.dto.ReportDto;
import com.sliit.washing_love_be.service.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/report")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    public ResponseEntity<?> addReport(@RequestBody ReportDto reportDto) throws Exception {
        ReportDto save = reportService.save(reportDto);
        if (save == null)
            return new ResponseEntity<>("Can't add report", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(save, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteReport(@RequestParam Long reportId) throws Exception {
        boolean delete = reportService.delete(reportId);
        if (!delete)
            return new ResponseEntity<>("Can't delete report", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>("Delete success", HttpStatus.OK);
    }

    @GetMapping("user")
    public ResponseEntity<?> findByUserId(@RequestParam Long userId) throws Exception {
        List<ReportDto> reportDtos = reportService.findByUserId(userId);
        if (reportDtos == null)
            return new ResponseEntity<>("Can't find report", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(reportDtos, HttpStatus.OK);
    }

    @GetMapping("report")
    public ResponseEntity<?> findByBookingId(@RequestParam Long bookingId) throws Exception {
        ReportDto reportDto = reportService.findByBookingId(bookingId);
        if (reportDto == null)
            return new ResponseEntity<>("Can't find report", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(reportDto, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllReports() {
        try {
            List<ReportDto> allReports = reportService.getAllReports();
            return new ResponseEntity<>(allReports, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
