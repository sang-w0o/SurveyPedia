package com.surveypedia.surveys.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

@NoArgsConstructor
@Getter
@Setter
public class SurveyDeadLineInfoDto extends SurveyInfoDto {

    private int s_code;
    private String writer;
    private String s_title;
    private int interest_count;
    private String written_date;
    private String end_date;

    public SurveyDeadLineInfoDto(Object[] objects) {
        this.writer = objects[0].toString();
        this.s_title = objects[1].toString();
        this.interest_count = Integer.parseInt(objects[2].toString());
        this.written_date = objects[3].toString();
        this.end_date = objects[4].toString();
        this.s_code = Integer.parseInt(objects[5].toString());
    }

    public HashMap<String, Object> convertMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("writer", writer);
        map.put("s_title", s_title);
        map.put("interest_count", interest_count);
        map.put("written_date", written_date);
        map.put("end_date", end_date);
        map.put("s_code", s_code);
        return map;
    }
}
