package com.surveypedia.surveys.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

@NoArgsConstructor
@Getter
@Setter
public class SurveySpareSampleInfoDto extends SurveyInfoDto{

    private int s_code;
    private String writer;
    private String s_title;
    private int interest_count;
    private String end_date;
    private int spare_sample_num;

    public SurveySpareSampleInfoDto(Object[] objects) {
        this.s_code = Integer.parseInt(objects[0].toString());
        this.writer = objects[1].toString();
        this.s_title = objects[2].toString();
        this.interest_count = Integer.parseInt(objects[3].toString());
        this.end_date = objects[4].toString();
        this.spare_sample_num = Integer.parseInt(objects[5].toString());
    }

    public HashMap<String, Object> convertMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("writer", writer);
        map.put("s_title", s_title);
        map.put("interest_count", interest_count);
        map.put("spare_sample_num", spare_sample_num);
        map.put("end_date", end_date);
        map.put("s_code", s_code);
        return map;
    }

}
