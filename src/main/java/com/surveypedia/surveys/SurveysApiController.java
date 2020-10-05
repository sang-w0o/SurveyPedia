package com.surveypedia.surveys;

import com.surveypedia.surveys.service.SurveyService;
import com.surveypedia.tools.WriteToClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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
}
