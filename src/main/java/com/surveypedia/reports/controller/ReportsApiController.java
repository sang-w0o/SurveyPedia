package com.surveypedia.reports.controller;

import com.surveypedia.reports.dto.ReportInsertRequestDto;
import com.surveypedia.reports.service.ReportsService;
import com.surveypedia.tools.WriteToClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/ReportGetAll.do")
    public void getAllReports(HttpServletResponse response) {
        WriteToClient.send(response, reportsService.getAllReports());
    }

    @PutMapping("/ReportApprove.do")
    public void approve(HttpServletRequest request, HttpServletResponse response) {
        WriteToClient.send(response, reportsService.approve(request));
    }

    @PutMapping("/ReportRemove.do")
    public void remove(HttpServletRequest request, HttpServletResponse response) {
        WriteToClient.send(response, reportsService.remove(request));
    }
}
