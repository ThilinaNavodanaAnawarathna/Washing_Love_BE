package com.sliit.washing_love_be.dto;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GateDto {
    private Long bookingId;;
    private boolean gate1;
    private boolean gate2;
}
