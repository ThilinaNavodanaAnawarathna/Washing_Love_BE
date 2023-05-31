package com.sliit.washing_love_be.service;


import com.sliit.washing_love_be.dto.BookingDto;

public interface BookingService {
    BookingDto save(BookingDto bookingDto)throws Exception;
}
