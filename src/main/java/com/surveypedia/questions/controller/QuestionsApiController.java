package com.surveypedia.questions.controller;

import com.surveypedia.questions.service.QuestionsService;
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
public class QuestionsApiController {

    private final QuestionsService questionsService;

    @PostMapping("/QuestionInsert.do")
    public void insert(@RequestBody String requestObject, HttpServletResponse response) {
        WriteToClient.send(response, questionsService.insert(requestObject));
    }

    @GetMapping("/GetQuestion.do")
    public void getQuestions(HttpServletRequest request, HttpServletResponse response) {
        WriteToClient.send(response, questionsService.getQuestionListByS_code(request));
    }
}
