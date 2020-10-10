package com.surveypedia.surveys.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class SurveyResultDto {

    private int s_code;
    private int q_number;
    private String q_title;
    private String q_type;
    private HashMap<String, Integer> choices;
    private List<String> subjectives;

    public HashMap<String, Object> convertMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("s_code", s_code);
        map.put("q_number", q_number);
        map.put("q_title", q_title);
        map.put("q_type", q_type);
        map.put("choices", choices);
        map.put("subjectives", subjectives);
        return map;
    }
}
