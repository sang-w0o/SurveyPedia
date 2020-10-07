package com.surveypedia.surveys.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

@NoArgsConstructor
@Getter
@Setter
public class SurveyCategoryInfoDto extends SurveyInfoDto {

    private int s_code;
    private String writer;
    private String s_title;
    private String written_date;
    private int interest_count;

    public SurveyCategoryInfoDto(Object[] objects) {
        this.s_code = Integer.parseInt(objects[0].toString());
        this.writer = objects[1].toString();
        this.s_title = objects[2].toString();
        this.written_date = objects[3].toString();
        this.interest_count = Integer.parseInt(objects[4].toString());
    }

    public HashMap<String, Object> convertMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("s_code", s_code);
        map.put("writer", writer);
        map.put("s_title", s_title);
        map.put("written_date", written_date);
        map.put("interest_count", interest_count);
        return map;
    }
}
