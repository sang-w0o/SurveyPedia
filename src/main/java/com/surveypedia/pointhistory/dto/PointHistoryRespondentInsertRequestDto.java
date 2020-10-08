package com.surveypedia.pointhistory.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PointHistoryRespondentInsertRequestDto {

    private String respondent;
    private int s_code;
}
