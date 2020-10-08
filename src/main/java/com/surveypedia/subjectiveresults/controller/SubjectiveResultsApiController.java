package com.surveypedia.subjectiveresults.controller;

import com.surveypedia.subjectiveresults.service.SubjectiveResultsService;
import com.surveypedia.tools.WriteToClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
public class SubjectiveResultsApiController {

    private final SubjectiveResultsService subjectiveResultsService;

    @PostMapping("/SubjectiveResultInsert.do")
    public void insert(@RequestBody String requestObject, HttpServletResponse response) {
        WriteToClient.send(response, subjectiveResultsService.insert(requestObject));
    }
}
