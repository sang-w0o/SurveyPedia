package com.surveypedia.surveys.controller;

import com.surveypedia.surveys.dto.SurveyInsertRequestDto;
import com.surveypedia.surveys.dto.SurveyResponseCheckDto;
import com.surveypedia.surveys.service.SurveyService;
import com.surveypedia.tools.WriteToClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
public class SurveysApiController {

    private final SurveyService surveyService;

    @GetMapping("/SurveyGetSurveyInfo.do")
    public void getSurveyInfo(HttpServletRequest request, HttpServletResponse response) {
        String type = request.getParameter("type");
        WriteToClient.send(response, surveyService.getSurveyInfo(request, type));
    }

    @GetMapping("/SurveyInsertCheck.do")
    public void checkWritable(HttpServletRequest request, HttpServletResponse response) {
        WriteToClient.send(response, surveyService.isRegisterable(request));
    }

    @PostMapping("/SurveyInsert.do")
    public void insert(@RequestBody SurveyInsertRequestDto requestDto, HttpServletResponse response) {
        WriteToClient.send(response, surveyService.insert(requestDto));
    }

    @GetMapping("/SurveyGetClosedList.do")
    public void getEndedSurveyLists(HttpServletRequest request, HttpServletResponse response) {
        WriteToClient.send(response, surveyService.getEndedSurveyLists(request));
    }

    @GetMapping("/SurveyGetByCategory.do")
    public void getSurveyByCategory(HttpServletRequest request, HttpServletResponse response) {
        WriteToClient.send(response, surveyService.getSuveyListByCategory(request));
    }

    @PostMapping("/SurveyCheckResponse.do")
    public void checkResponse(@RequestBody SurveyResponseCheckDto checkDto, HttpServletRequest request, HttpServletResponse response) {
        WriteToClient.send(response, surveyService.checkResponse(checkDto));
    }
}
