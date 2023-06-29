package com.sliit.washing_love_be.service;

import com.sliit.washing_love_be.dto.ReportDto;

import java.util.List;

public interface ReportService {
    ReportDto save(ReportDto reportDto) throws Exception;

    boolean delete(Long reportId) throws Exception;

    ReportDto findByBookingId(Long bookingId) throws Exception;

    List<ReportDto> getAllReports() throws Exception;
}
