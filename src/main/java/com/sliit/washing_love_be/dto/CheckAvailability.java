package com.sliit.washing_love_be.dto;

import lombok.*;

import java.sql.Time;
import java.util.Date;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckAvailability {
    private Date date;
    private Time time;
}
