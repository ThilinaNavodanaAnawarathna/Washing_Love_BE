package com.sliit.washing_love_be.dto;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBasicDetails {
    private Long id;
    private String firstName;

    private String lastName;
}
