package com.surveypedia.surveys.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public abstract class SurveyInfoDto {

    private int s_code;
    private String writer;
    private String s_title;
    private int interest_count;
    private String end_date;
}
