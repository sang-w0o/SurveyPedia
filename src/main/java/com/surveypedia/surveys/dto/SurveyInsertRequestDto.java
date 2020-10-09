package com.surveypedia.surveys.dto;

import com.surveypedia.domain.surveys.Survey;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SurveyInsertRequestDto {

    private String s_title;
    private String email;
    private String c_code;
    private String s_public;

    public Survey toEntity(SurveyInsertRequestDto requestDto, Integer s_id) {
        return Survey.builder().stitle(s_title).email(email).ccode(c_code).
                spublic(s_public).sreported("N").s_id(s_id).price(0).build();
    }
}
