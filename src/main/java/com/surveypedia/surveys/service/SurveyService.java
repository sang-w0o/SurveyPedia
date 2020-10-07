package com.surveypedia.surveys.service;

import com.surveypedia.domain.members.Members;
import com.surveypedia.domain.pointhistory.PointHistoryRepository;
import com.surveypedia.domain.surveys.Survey;
import com.surveypedia.domain.surveys.SurveysRepository;
import com.surveypedia.surveys.dto.*;
import com.surveypedia.surveys.exception.SurveyGetEndSurveyException;
import com.surveypedia.surveys.exception.SurveyGetSurveyException;
import com.surveypedia.surveys.exception.SurveyInsertCheckException;
import com.surveypedia.surveys.exception.SurveyInsertException;
import com.surveypedia.tools.ObjectMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SurveyService {

    private static final int PAGESIZE = 10;
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
            Integer s_code = surveysRepository.save(survey).getScode();
            jsonObject.put("result", true);
            jsonObject.put("s_code", s_code);
            jsonObject.put("message", "설문지가 정상적으로 등록 되었습니다!");
        } catch(Exception exception) {
            exception.printStackTrace();
            jsonObject = ObjectMaker.getJSONObjectWithException(new SurveyInsertException());
        }
        return jsonObject;
    }

    public org.json.simple.JSONObject getEndedSurveyLists(HttpServletRequest request) {
        org.json.simple.JSONObject jsonObject = ObjectMaker.getSimpleJSONObject();
        org.json.simple.JSONArray jsonArray = ObjectMaker.getSimpleJSONArray();
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("userInfo") == null) {
            jsonObject.put("respondent", null);
        } else {
            jsonObject.put("respondent", ((Members)session.getAttribute("userInfo")).getEmail());
        }
        try {
            List<SurveyEndedInfoDto> list = surveysRepository.getEndedSurveyLists().stream().map(SurveyEndedInfoDto::new).collect(Collectors.toList());
            for(SurveyEndedInfoDto dto : list) {
                org.json.simple.JSONObject jTemp = ObjectMaker.getSimpleJSONObject();
                jTemp.putAll(dto.convertMap());
                jsonArray.add(jTemp);
            }
            jsonObject.put("result", true);
            jsonObject.put("endSurveyList", jsonArray);
        } catch(Exception exception) {
            exception.printStackTrace();
            jsonObject = ObjectMaker.getJSONObjectWithException(new SurveyGetEndSurveyException());
        }
        return jsonObject;
    }

    public org.json.simple.JSONObject getSuveyListByCategory(HttpServletRequest request) {
        org.json.simple.JSONObject jsonObject = ObjectMaker.getSimpleJSONObject();
        org.json.simple.JSONArray jsonArray = ObjectMaker.getSimpleJSONArray();
        String strPage = request.getParameter("page");
        String mode = request.getParameter("mode");
        String c_code = request.getParameter("c_code");

        int realPage = 1;
        if(strPage != null) {
            realPage = Integer.parseInt(strPage);
        }

        c_code = (c_code == null) ? "" : c_code;
        mode = (mode == null) ? "" : mode;

        int totalRecords = surveysRepository.getTotalCountByCategory(c_code);
        int pageCount = (int)Math.ceil((double)totalRecords / PAGESIZE);

        switch(mode) {
            case "first":
                realPage = 1;
                break;
            case "last":
                realPage = pageCount;
                break;
            case "prev":
                if( --realPage < 1) realPage = 1;
                break;
            case "next":
                if( ++realPage > pageCount) realPage = pageCount;
                break;
            default:
                if(realPage < 1) realPage = 1;
                if(realPage > pageCount) realPage = pageCount;
                break;
        }
        int startPos = ((realPage - 1) * PAGESIZE < 0) ? 0 : (realPage - 1) * PAGESIZE;
        try {
            List<SurveyCategoryInfoDto> list = surveysRepository.getSurveyListByCategoryAndPage(c_code, startPos, PAGESIZE).stream().map(SurveyCategoryInfoDto::new).collect(Collectors.toList());
            for (SurveyCategoryInfoDto dto : list) {
                org.json.simple.JSONObject jTemp = ObjectMaker.getSimpleJSONObject();
                jTemp.putAll(dto.convertMap());
                jsonArray.add(jTemp);
            }
            jsonObject.put("respondent", ((Members)request.getSession(false).getAttribute("userInfo")).getEmail());
            jsonObject.put("surveys", jsonArray);
            jsonObject.put("result", true);
            jsonObject.put("page", realPage);
            jsonObject.put("c_code", c_code);
            jsonObject.put("pageCount", pageCount);
        } catch(Exception exception) {
            jsonObject = ObjectMaker.getJSONObjectWithException(new Exception("설문 목록을 가져오는데 실패했습니다."));
        }
        return jsonObject;
    }
}
