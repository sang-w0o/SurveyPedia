package com.surveypedia.surveys.dto;

import com.surveypedia.domain.surveys.Survey;
import com.surveypedia.tools.SQL;
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
        return Survey.builder().s_title(s_title).email(email).c_code(c_code).
                s_public(s_public).s_reported("N").s_id(s_id).price(0).build();
    }
}
