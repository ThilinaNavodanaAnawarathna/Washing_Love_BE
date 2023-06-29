package com.sliit.washing_love_be.dto;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportDto {
    private Long id;
    private BookingDto booking;
    private String url;
}
