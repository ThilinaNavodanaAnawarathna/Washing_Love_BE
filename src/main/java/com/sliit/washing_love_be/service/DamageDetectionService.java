package com.sliit.washing_love_be.service;

import org.springframework.web.multipart.MultipartFile;

public interface DamageDetectionService {
    Object detectDamages(MultipartFile front,
                         MultipartFile back,
                         MultipartFile left,
                         MultipartFile right,
                         Long bookingId) throws Exception;
}
