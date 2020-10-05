package com.surveypedia.surveys.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SurveyHistoryInfoDto {

    private int s_code;
    private String s_title;
    private String email;
    private String c_code;

    public SurveyHistoryInfoDto(Object[] objects) {
        this.s_code = Integer.parseInt(objects[0].toString());
        this.s_title = objects[1].toString();
        this.email = objects[2].toString();
        this.c_code = objects[3].toString();
    }
}
