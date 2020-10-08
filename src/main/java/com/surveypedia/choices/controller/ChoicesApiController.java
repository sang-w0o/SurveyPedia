package com.surveypedia.choices.controller;

import com.surveypedia.choices.service.ChoicesService;
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
public class ChoicesApiController {

    private final ChoicesService choicesService;

    @PostMapping("/ChoiceInsert.do")
    public void insert(@RequestBody String requestObject, HttpServletResponse response) {
        WriteToClient.send(response, choicesService.insert(requestObject));
    }

    @GetMapping("/GetChoice.do")
    public void getChoices(HttpServletRequest request, HttpServletResponse response) {
        WriteToClient.send(response, choicesService.getChoices(request));
    }
}
