package com.surveypedia.surveys.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

@NoArgsConstructor
@Getter
@Setter
public class SurveyEndedInfoDto extends SurveyInfoDto{

    private int s_code;
    private String writer;
    private String email;
    private String s_title;
    private String c_desc;
    private int spare_sample_num;
    private int price;

    public SurveyEndedInfoDto(Object[] objects) {
        this.s_code = Integer.parseInt(objects[0].toString());
        this.writer = objects[1].toString();
        this.email = objects[2].toString();
        this.s_title = objects[3].toString();
        this.c_desc = objects[4].toString();
        this.spare_sample_num = Integer.parseInt(objects[5].toString());
        this.price = Integer.parseInt(objects[6] == null ? "0" : objects[6].toString());
    }

    public HashMap<String, Object> convertMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("s_code", s_code);
        map.put("writer", writer);
        map.put("email", email);
        map.put("s_title", s_title);
        map.put("c_desc", c_desc);
        map.put("spare_sample_num", spare_sample_num);
        map.put("price", price);
        return map;
    }
}
