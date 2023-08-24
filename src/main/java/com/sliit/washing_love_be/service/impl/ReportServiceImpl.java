package com.sliit.washing_love_be.service.impl;

import com.sliit.washing_love_be.dto.ReportDto;
import com.sliit.washing_love_be.entity.Booking;
import com.sliit.washing_love_be.entity.Report;
import com.sliit.washing_love_be.entity.User;
import com.sliit.washing_love_be.repository.BookingRepository;
import com.sliit.washing_love_be.repository.ReportRepository;
import com.sliit.washing_love_be.repository.UserRepository;
import com.sliit.washing_love_be.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    private final ModelMapper modelMapper;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    public ReportServiceImpl(ReportRepository reportRepository, ModelMapper modelMapper, BookingRepository bookingRepository, UserRepository userRepository) {
        this.reportRepository = reportRepository;
        this.modelMapper = modelMapper;
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ReportDto save(ReportDto reportDto) throws Exception {
        try {
            Report report = modelMapper.map(reportDto, Report.class);
            Report save = reportRepository.save(report);
            return modelMapper.map(save, ReportDto.class);
        } catch (Exception e) {
            log.error("ReportService : Can't save report | Error : {}", e.getMessage());
            throw new Exception("Can't save report details");
        }
    }

    @Override
    public boolean delete(Long reportId) throws Exception {
        try {
            Optional<Report> byId = reportRepository.findById(reportId);
            if (!byId.isPresent())
                throw new Exception("Can't find report details");
            reportRepository.delete(byId.get());
            return true;
        } catch (Exception e) {
            log.error("ReportService : Can't delete report | Error : {}", e.getMessage());
            throw new Exception("Can't delete report details");
        }
    }

    @Override
    public ReportDto findByBookingId(Long bookingId) throws Exception {
        try {
            Optional<Booking> booking = bookingRepository.findById(bookingId);
            if (!booking.isPresent())
                throw new Exception("Can't find booking details");
            List<Report> byId = reportRepository.findByBooking(booking.get());
            if (!byId.isEmpty())
                throw new Exception("Can't find report details");
            return modelMapper.map(byId.get(0), ReportDto.class);
        } catch (Exception e) {
            log.error("ReportService : Can't find report | Error : {}", e.getMessage());
            throw new Exception("Can't find report details");
        }
    }

    @Override
    public List<ReportDto> findByUserId(Long userId) throws Exception {
        try {
            Optional<User> user = userRepository.findById(userId);
            if (!user.isPresent())
                throw new Exception("Can't find user details");
            List<Report> byId = reportRepository.findByUser(user.get());
            if (byId == null)
                throw new Exception("Can't find report details");
            return byId.stream()
                    .map(element -> modelMapper.map(element, ReportDto.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("ReportService : Can't find report | Error : {}", e.getMessage());
            throw new Exception("Can't find report details");
        }
    }

    @Override
    public List<ReportDto> getAllReports() throws Exception {
        try {
            return reportRepository.findAll().stream()
                    .map(element -> modelMapper.map(element, ReportDto.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("ReportService : Can't get All reports | Error : {}", e.getMessage());
            throw new Exception(e.getMessage());
        }
    }
}
