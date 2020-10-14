package com.surveypedia.surveys.service;

import com.surveypedia.domain.categories.CategoriesRepository;
import com.surveypedia.domain.choiceresults.ChoiceResultsRepository;
import com.surveypedia.domain.choices.ChoicesRepository;
import com.surveypedia.domain.interests.InterestsRepository;
import com.surveypedia.domain.members.Members;
import com.surveypedia.domain.pointhistory.PointHistoryRepository;
import com.surveypedia.domain.pointhistory.PointHistoryType;
import com.surveypedia.domain.questions.Questions;
import com.surveypedia.domain.questions.QuestionsRepository;
import com.surveypedia.domain.subjectiveresults.SubjectiveResultsRepository;
import com.surveypedia.domain.surveys.Survey;
import com.surveypedia.domain.surveys.SurveysRepository;
import com.surveypedia.surveys.dto.*;
import com.surveypedia.surveys.exception.*;
import com.surveypedia.tools.ObjectMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SurveyService {

    private static final int PAGESIZE = 10;
    private final SurveysRepository surveysRepository;
    private final PointHistoryRepository pointHistoryRepository;
    private final ChoiceResultsRepository choiceResultsRepository;
    private final SubjectiveResultsRepository subjectiveResultsRepository;
    private final CategoriesRepository categoriesRepository;
    private final InterestsRepository interestsRepository;
    private final QuestionsRepository questionsRepository;
    private final ChoicesRepository choicesRepository;

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
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
            HttpSession session = request.getSession(false);
            if(session == null || session.getAttribute("userInfo") == null) {
                jsonObject.put("respondent", null);
            } else {
                jsonObject.put("respondent", ((Members) request.getSession(false).getAttribute("userInfo")).getEmail());
            }
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

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public org.json.simple.JSONObject isRegisterable(HttpServletRequest request) {
        org.json.simple.JSONObject jsonObject = ObjectMaker.getSimpleJSONObject();
        String email = ((Members)request.getSession(false).getAttribute("userInfo")).getEmail();
        int writeCount = surveysRepository.getWriteCount(email); int participateCount = pointHistoryRepository.getParticipateCount(email); try { boolean result = (((participateCount / 2) - writeCount) > -1); if(!result) throw new SurveyInsertCheckException(); jsonObject.put("result", true); } catch(SurveyInsertCheckException exception) {
            jsonObject = ObjectMaker.getJSONObjectWithException(exception);
        }
        return jsonObject;
    }

    @Transactional
    @SuppressWarnings("unchecked")
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
            jsonObject = ObjectMaker.getJSONObjectWithException(new SurveyInsertException());
        }
        return jsonObject;
    }

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
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
            jsonObject = ObjectMaker.getJSONObjectWithException(new SurveyGetEndSurveyException());
        }
        return jsonObject;
    }

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
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

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public org.json.simple.JSONObject checkResponse(SurveyResponseCheckDto checkDto) {
        org.json.simple.JSONObject jsonObject = ObjectMaker.getSimpleJSONObject();
        String respondent = checkDto.getRespondent();
        int s_code = checkDto.getS_code();
        try {
            if(surveysRepository.findByEmailAndScode(respondent, s_code) != null) {
                throw new SurveyRespondentIsWriterException();
            }
            if(subjectiveResultsRepository.findByScodeAndRespondent(s_code, respondent).size() != 0) {
                throw new SurveyCheckResponseException();
            }

            if(choiceResultsRepository.findByScodeAndRespondent(s_code, respondent).size() != 0) {
                throw new SurveyCheckResponseException();
            }
            jsonObject.put("result", true);
        } catch(SurveyRespondentIsWriterException | SurveyCheckResponseException exception) {
            jsonObject = ObjectMaker.getJSONObjectWithException(exception);
        }
        return jsonObject;
    }

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public org.json.simple.JSONObject getSurvey(HttpServletRequest request) {
        int s_code = Integer.parseInt(request.getParameter("s_code"));
        org.json.simple.JSONObject jsonObject = ObjectMaker.getSimpleJSONObject();
        try {
            Survey survey = surveysRepository.findByScode(s_code);
            String c_desc = categoriesRepository.getDescByC_code(survey.getCcode());
            jsonObject.put("result", true);
            jsonObject.put("email", survey.getEmail());
            jsonObject.put("c_desc", c_desc);
            jsonObject.put("s_title", survey.getStitle());
            jsonObject.put("s_code", s_code);
        } catch(Exception exception) {
            jsonObject = ObjectMaker.getJSONObjectWithException(new SurveyGetSurveyException());
        }
        return jsonObject;
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public org.json.simple.JSONObject updatePrice(HttpServletRequest request) {
        org.json.simple.JSONObject jsonObject = ObjectMaker.getSimpleJSONObject();
        int s_code = Integer.parseInt(request.getParameter("s_code"));
        try {
            int totalSamples = pointHistoryRepository.getSampleCounts(s_code);
            int totalInterests = interestsRepository.getInterestCounts(s_code);
            int finalPrice = totalSamples * 2 + totalInterests;
            Survey survey = surveysRepository.findByScode(s_code);
            survey.updatePrice(finalPrice);
            jsonObject.put("result", true);
        } catch(Exception exception) {
            jsonObject = ObjectMaker.getJSONObjectWithException(new SurveyPriceUpdateException());
        }
        return jsonObject;
    }

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public org.json.simple.JSONObject getMySurveys(HttpServletRequest request) {
        org.json.simple.JSONObject jsonObject = ObjectMaker.getSimpleJSONObject();
        org.json.simple.JSONArray jsonArray = ObjectMaker.getSimpleJSONArray();
        String email = ((Members)request.getSession(false).getAttribute("userInfo")).getEmail();
        String myType = request.getParameter("myType");
        String mode = request.getParameter("mode");
        mode = (mode == null) ? "" : mode;
        int page = 0;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch(NumberFormatException exception) {
        }
        int totalRecords = 0;
        try {
            switch (myType) {
                case "MYCURRENT":
                    totalRecords = surveysRepository.getTotalCountOfCurrentSurveysByEmail(email);
                    break;
                case "MYENDED":
                    totalRecords = surveysRepository.getTotalCountOfEndedSurveysByEmail(email);
                    break;
                case "INTERESTCURRENT":
                    totalRecords = interestsRepository.getInterestCountsByEmail(email);
                    break;
                case "INTERESTENDED":
                    totalRecords = interestsRepository.getInterestCountsOfEndedSurveyByEmail(email);
                    break;
                case "PURCHASED":
                    totalRecords = pointHistoryRepository.getTotalCountsOfPurchaseByEmail(email);
                    break;
                default:
                    totalRecords = 0;
                    break;
            }

            int pageCount = (int) Math.ceil((double) totalRecords / PAGESIZE);
            switch (mode) {
                case "first":
                    page = 0;
                    break;
                case "last":
                    page = pageCount;
                    break;
                case "prev":
                    if (--page < 1) page = 1;
                    break;
                case "next":
                    if (++page > pageCount) page = pageCount;
                    break;
                default:
                    if (page < 1) page = 1;
                    if (page > pageCount) page = pageCount;
                    break;
            }

            int startPos = ((page - 1) * PAGESIZE < 0) ? 0 : (page - 1) * PAGESIZE;
            List<SurveyCategoryInfoDto> list = null;
            switch (myType) {
                case "MYCURRENT":
                    list = surveysRepository.getCurrentSurveyInfoByEmail(email, startPos, PAGESIZE).stream().map(SurveyCategoryInfoDto::new).collect(Collectors.toList());
                    break;
                case "MYENDED":
                    list = surveysRepository.getEndedSurveyInfoByEmail(email, startPos, PAGESIZE).stream().map(SurveyCategoryInfoDto::new).collect(Collectors.toList());
                    break;
                case "INTERESTCURRENT":
                    list = surveysRepository.getInterestCurrentSurveyInfoByEmail(email, startPos, PAGESIZE).stream().map(SurveyCategoryInfoDto::new).collect(Collectors.toList());
                    break;
                case "INTERESTENDED":
                    list = surveysRepository.getInterestEndedSurveyInfoByEmail(email, startPos, PAGESIZE).stream().map(SurveyCategoryInfoDto::new).collect(Collectors.toList());
                    break;
                case "PURCHASED":
                    list = surveysRepository.getPurchasedSurveyInfoByEmail(email, startPos, PAGESIZE).stream().map(SurveyCategoryInfoDto::new).collect(Collectors.toList());
                    break;
            }
            for(SurveyCategoryInfoDto dto : list) {
                org.json.simple.JSONObject jTemp = ObjectMaker.getSimpleJSONObject();
                jTemp.putAll(dto.convertMap());
                jsonArray.add(jTemp);
            }
            jsonObject.put("respondent", ((Members)request.getSession(false).getAttribute("userInfo")).getEmail());
            jsonObject.put("surveys", jsonArray);
        } catch(Exception exception) {
            jsonObject = ObjectMaker.getJSONObjectWithException(new SurveyGetSurveyException());
        }
        return jsonObject;
    }

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public org.json.simple.JSONObject checkResultView(HttpServletRequest request) {
        org.json.simple.JSONObject jsonObject = ObjectMaker.getSimpleJSONObject();
        String email = ((Members)request.getSession(false).getAttribute("userInfo")).getEmail();
        int s_code = Integer.parseInt(request.getParameter("s_code"));
        try {
            if ((surveysRepository.findByEmailAndScode(email, s_code) != null) || (pointHistoryRepository.findByEmailAndPhtypeAndScode(email, PointHistoryType.B, s_code) != null)) {
                jsonObject.put("result", true);
            } else throw new SurveyResultNotViewableException();
        } catch(Exception exception) {
            jsonObject = ObjectMaker.getJSONObjectWithException(new SurveyResultNotViewableException());
        }
        return jsonObject;
    }

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public org.json.simple.JSONObject getResults(HttpServletRequest request) {
        org.json.simple.JSONObject jsonObject = ObjectMaker.getSimpleJSONObject();
        org.json.simple.JSONArray jsonArray = ObjectMaker.getSimpleJSONArray();
        int s_code = Integer.parseInt(request.getParameter("s_code"));
        try {
            Survey survey = surveysRepository.findByScode(s_code);
            String c_desc = categoriesRepository.getDescByC_code(survey.getCcode());
            String title = survey.getStitle();
            String email = survey.getEmail();
            int sample_num = pointHistoryRepository.getSampleCounts(s_code);
            List<Questions> questionsList = questionsRepository.findByScode(s_code);
            ArrayList<SurveyResultDto> resultList = new ArrayList<>();
            List<Integer> choicesList = null;
            List<String> subjectiveList = null;
            HashMap<String, Integer> choiceResults = new HashMap<>();
            for(Questions question : questionsList) {
                SurveyResultDto resultDto = new SurveyResultDto();
                resultDto.setS_code(s_code);
                resultDto.setQ_number(question.getQnumber());
                resultDto.setQ_title(question.getQtitle());
                resultDto.setQ_type(question.getQtype());
                if(question.getQtype().equals("C")) {
                    choicesList = choicesRepository.getChoiceNumbers(s_code, question.getQnumber());
                    for(Integer choice_number : choicesList) {
                        int value = choiceResultsRepository.getChoiceCounts(s_code, question.getQnumber(), choice_number);
                        String choice_content = choicesRepository.findByScodeAndQnumberAndChoicenum(s_code, question.getQnumber(), choice_number).getChoicecontent();
                        choiceResults.put(choice_content, value);
                    }
                    resultDto.setChoices(choiceResults);
                } else {
                    subjectiveList = subjectiveResultsRepository.getAnswers(s_code, question.getQnumber());
                    resultDto.setSubjectives(subjectiveList);
                }
                resultList.add(resultDto);
            }
            jsonObject.put("result", true);
            jsonObject.put("email", email);
            jsonObject.put("category", c_desc);
            jsonObject.put("title", title);
            jsonObject.put("sample", sample_num);
            for(SurveyResultDto dto : resultList) {
                org.json.simple.JSONObject jTemp = ObjectMaker.getSimpleJSONObject();
                jTemp.putAll(dto.convertMap());
                jsonArray.add(jTemp);
            }
            jsonObject.put("list", jsonArray);
        } catch(Exception exception) {
            jsonObject = ObjectMaker.getJSONObjectWithException(new SurveyGetSurveyException());
        }
        return jsonObject;
    }
}
