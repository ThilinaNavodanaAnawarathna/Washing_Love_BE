package com.sliit.washing_love_be.service;

import com.sliit.washing_love_be.dto.BookingDto;
import org.springframework.web.multipart.MultipartFile;

public interface NumberPlateService {
    String checkNumberPlate(MultipartFile numberPlate) throws Exception;
}
