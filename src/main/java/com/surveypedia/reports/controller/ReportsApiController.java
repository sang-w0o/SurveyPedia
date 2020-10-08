package com.surveypedia.reports.controller;

import com.surveypedia.reports.service.ReportsService;
import com.surveypedia.tools.WriteToClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
public class ReportsApiController {

    private final ReportsService reportsService;

    @GetMapping("/ReportCheck.do")
    public void reportCheck(HttpServletRequest request, HttpServletResponse response) {
        WriteToClient.send(response, reportsService.reportCheck(request));
    }
}
