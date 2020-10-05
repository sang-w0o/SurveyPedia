package com.surveypedia.pointhistory.controller;

import com.surveypedia.pointhistory.service.PointHistoryService;
import com.surveypedia.tools.WriteToClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
public class PointHistoryApiController {

    private final PointHistoryService pointHistoryService;

    @GetMapping("/PointHistoryGetList.do")
    public void getPointHistoryList(HttpServletRequest request, HttpServletResponse response) {
        WriteToClient.send(response, pointHistoryService.getPointHistoryList(request));
    }
}
