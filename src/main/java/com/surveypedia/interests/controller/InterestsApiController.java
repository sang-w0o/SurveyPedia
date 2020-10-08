package com.surveypedia.interests.controller;

import com.surveypedia.interests.service.InterestsService;
import com.surveypedia.tools.WriteToClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
public class InterestsApiController {

    private final InterestsService interestsService;

    public void interestCheck(HttpServletRequest request, HttpServletResponse response) {
        WriteToClient.send(response, interestsService.interestCheck(request));
    }
}
