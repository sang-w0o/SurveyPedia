package com.surveypedia.reports.controller;

import com.surveypedia.reports.dto.ReportInsertRequestDto;
import com.surveypedia.reports.service.ReportsService;
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
public class ReportsApiController {

    private final ReportsService reportsService;

    @GetMapping("/ReportCheck.do")
    public void reportCheck(HttpServletRequest request, HttpServletResponse response) {
        WriteToClient.send(response, reportsService.reportCheck(request));
    }

    @PostMapping("/ReportInsert.do")
    public void reportInsert(@RequestBody ReportInsertRequestDto requestDto, HttpServletResponse response) {
        WriteToClient.send(response, reportsService.insert(requestDto));
    }
}
