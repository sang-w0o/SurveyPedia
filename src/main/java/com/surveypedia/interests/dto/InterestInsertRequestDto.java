package com.surveypedia.interests.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class InterestInsertRequestDto {

    private String email;
    private int s_code;
}
