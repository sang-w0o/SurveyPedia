package com.surveypedia.surveys.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

@NoArgsConstructor
@Getter
@Setter
public class SurveyEndInfoDto extends SurveyInfoDto {

    private int s_code;
    private String writer;
    private String s_title;
    private int interest_count;
    private String end_date;
    private int price;

    public HashMap<String, Object> convertMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("s_code", s_code);
        map.put("writer", writer);
        map.put("s_title", s_title);
        map.put("interest_count", interest_count);
        map.put("end_date", end_date);
        map.put("price", price);
        return map;
    }

    public SurveyEndInfoDto(Object[] objects) {
        this.s_code = Integer.parseInt(objects[0].toString());
        this.writer = objects[1].toString();
        this.s_title = objects[2].toString();
        this.interest_count = Integer.parseInt(objects[3].toString());
        this.end_date = objects[4].toString();
        this.price = Integer.parseInt(objects[5].toString());
    }
}
