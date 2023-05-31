package com.sliit.washing_love_be.dto;

import com.sliit.washing_love_be.enumz.BookingStatus;
import lombok.*;

import java.sql.Time;
import java.util.Date;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {
    private Long id;
    private UserBasicDetails user;
    private VehicleBasicDetails vehicle;
    private Date date;
    private Time startTime;
    private Time endTime;
    private BookingStatus bookingStatus;

}
