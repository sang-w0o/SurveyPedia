package com.surveypedia.choices.controller;

import com.surveypedia.choices.service.ChoicesService;
import com.surveypedia.tools.WriteToClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
public class ChoicesApiController {

    private final ChoicesService choicesService;

    @PostMapping("/ChoiceInsert.do")
    public void insert(@RequestBody String requestObject, HttpServletResponse response) {
        WriteToClient.send(response, choicesService.insert(requestObject));
    }
}
