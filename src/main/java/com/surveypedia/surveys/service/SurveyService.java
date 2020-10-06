package com.surveypedia.surveys.service;

import com.surveypedia.domain.members.Members;
import com.surveypedia.domain.pointhistory.PointHistoryRepository;
import com.surveypedia.domain.surveys.Survey;
import com.surveypedia.domain.surveys.SurveysRepository;
import com.surveypedia.surveys.dto.*;
import com.surveypedia.surveys.exception.SurveyInsertCheckException;
import com.surveypedia.surveys.exception.SurveyInsertException;
import com.surveypedia.tools.ObjectMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SurveyService {

    private final SurveysRepository surveysRepository;
    private final PointHistoryRepository pointHistoryRepository;

    public org.json.simple.JSONObject getSurveyInfo(HttpServletRequest request, String type) {
        org.json.simple.JSONObject jsonObject = ObjectMaker.getSimpleJSONObject();
        List<SurveyInfoDto> list = null;
        try {
            switch (type) {
                case "deadLine":
                    list = surveysRepository.getSurveyInfoByDeadLine().stream().map(SurveyDeadLineInfoDto::new).collect(Collectors.toList());
                    break;

                case "spareSampleNum":
                    list = surveysRepository.getSurveyInfoBySpareSampleNum().stream().map(SurveySpareSampleInfoDto::new).collect(Collectors.toList());
                    break;

                case "endSurvey":
                   list = surveysRepository.getSurveyInfoByEnd().stream().map(SurveyEndInfoDto::new).collect(Collectors.toList());
                   break;
                default:
                    break;
            }
            org.json.simple.JSONArray jsonArray = ObjectMaker.getSimpleJSONArray();
            for(SurveyInfoDto dto : list) {
                org.json.simple.JSONObject jTemp = ObjectMaker.getSimpleJSONObject();
                switch(type) {
                    case "deadLine":
                        jTemp.putAll(((SurveyDeadLineInfoDto)dto).convertMap());
                        break;
                    case "spareSampleNum":
                        jTemp.putAll(((SurveySpareSampleInfoDto)dto).convertMap());
                        break;
                    case "endSurvey":
                        jTemp.putAll(((SurveyEndInfoDto)dto).convertMap());
                        break;
                }
                jsonArray.add(jTemp);
            }
            jsonObject.put("list", jsonArray);
            jsonObject.put("errno", 0);
        } catch(Exception exception) {
            exception.printStackTrace();
            jsonObject = ObjectMaker.getJSONObjectWithException(exception);
            jsonObject.put("errno", 1);
        }
        Members member = (Members)request.getSession(false).getAttribute("userInfo");
        if(member == null) jsonObject.put("respondent", null);
        else jsonObject.put("respondent", member.getEmail());
        return jsonObject;
    }

    public org.json.simple.JSONObject isRegisterable(HttpServletRequest request) {
        org.json.simple.JSONObject jsonObject = ObjectMaker.getSimpleJSONObject();
        String email = ((Members)request.getSession(false).getAttribute("userInfo")).getEmail();
        int writeCount = surveysRepository.getWriteCount(email);
        int participateCount = pointHistoryRepository.getParticipateCount(email);
        try {
            boolean result = (((participateCount / 2) - writeCount) > -1);
            if(!result) throw new SurveyInsertCheckException();
            jsonObject.put("result", true);
        } catch(SurveyInsertCheckException exception) {
            jsonObject = ObjectMaker.getJSONObjectWithException(exception);
        }
        return jsonObject;
    }

    public org.json.simple.JSONObject insert(SurveyInsertRequestDto requestDto) {
        org.json.simple.JSONObject jsonObject = ObjectMaker.getSimpleJSONObject();
        Integer last_s_id = surveysRepository.getLastS_id(requestDto.getEmail());
        if(last_s_id == null) last_s_id = 0;
        Survey survey = requestDto.toEntity(requestDto, last_s_id + 1);
        try {
            surveysRepository.save(survey);
            jsonObject.put("result", true);
        } catch(Exception exception) {
            exception.printStackTrace();
            jsonObject = ObjectMaker.getJSONObjectWithException(new SurveyInsertException());
        }
        return jsonObject;
    }
}
