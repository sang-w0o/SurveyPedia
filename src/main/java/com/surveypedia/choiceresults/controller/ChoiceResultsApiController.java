package com.surveypedia.choiceresults.controller;

import com.surveypedia.choiceresults.service.ChoiceResultsService;
import com.surveypedia.tools.WriteToClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
public class ChoiceResultsApiController {

    private final ChoiceResultsService choiceResultsService;

    @PostMapping("/ChoiceResultInsert.do")
    public void insert(@RequestBody String requestObject, HttpServletResponse response) {
        WriteToClient.send(response, choiceResultsService.insert(requestObject));
    }
}
