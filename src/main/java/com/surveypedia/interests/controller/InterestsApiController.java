package com.surveypedia.interests.controller;

import com.surveypedia.interests.dto.InterestInsertRequestDto;
import com.surveypedia.interests.service.InterestsService;
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
public class InterestsApiController {

    private final InterestsService interestsService;

    @GetMapping("/InterestCheck.do")
    public void interestCheck(HttpServletRequest request, HttpServletResponse response) {
        WriteToClient.send(response, interestsService.interestCheck(request));
    }

    @PostMapping("/InterestInsert.do")
    public void insert(@RequestBody InterestInsertRequestDto requestDto, HttpServletResponse response) {
        WriteToClient.send(response, interestsService.insert(requestDto));
    }
}
