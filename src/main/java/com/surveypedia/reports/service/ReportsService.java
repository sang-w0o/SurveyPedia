package com.surveypedia.reports.service;

import com.surveypedia.domain.reports.ReportState;
import com.surveypedia.domain.reports.Reports;
import com.surveypedia.domain.reports.ReportsRepository;
import com.surveypedia.domain.surveys.SurveysRepository;
import com.surveypedia.reports.dto.ReportInsertRequestDto;
import com.surveypedia.reports.dto.ReportResponseDto;
import com.surveypedia.reports.exception.ReportApproveException;
import com.surveypedia.reports.exception.ReportCheckException;
import com.surveypedia.reports.exception.ReportInsertException;
import com.surveypedia.reports.exception.ReportRemoveException;
import com.surveypedia.tools.ObjectMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReportsService {

    private final ReportsRepository reportsRepository;
    private final SurveysRepository surveysRepository;

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public org.json.simple.JSONObject reportCheck(HttpServletRequest request) {
        int s_code = Integer.parseInt(request.getParameter("s_code"));
        String reporter = request.getParameter("reporter");
        org.json.simple.JSONObject jsonObject = ObjectMaker.getSimpleJSONObject();
        try {
            Reports report = reportsRepository.findByScodeAndReporter(s_code, reporter);
            if(report != null) throw new ReportCheckException();
            jsonObject.put("result", true);
        } catch(ReportCheckException exception) {
            jsonObject = ObjectMaker.getJSONObjectWithException(exception);
        }
        return jsonObject;
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public org.json.simple.JSONObject insert(ReportInsertRequestDto requestDto) {
        org.json.simple.JSONObject jsonObject = ObjectMaker.getSimpleJSONObject();
        try {
            Reports report = Reports.builder().scode(requestDto.getS_code())
                    .cause(requestDto.getCause())
                    .reporter(requestDto.getReporter())
                    .rtype("W")
                    .reportstate(ReportState.N).build();
            reportsRepository.save(report);
            jsonObject.put("result", true);
            jsonObject.put("message", "신고가 정상적으로 접수되었습니다.");
        } catch(Exception exception) {
            jsonObject = ObjectMaker.getJSONObjectWithException(new ReportInsertException());
        }
        return jsonObject;
    }

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public org.json.simple.JSONObject getAllReports() {
        org.json.simple.JSONObject jsonObject = ObjectMaker.getSimpleJSONObject();
        org.json.simple.JSONArray jsonArray = ObjectMaker.getSimpleJSONArray();
        List<ReportResponseDto> reportsList = reportsRepository.findByReportstate(ReportState.N).stream().map(ReportResponseDto::new).collect(Collectors.toList());
        for(ReportResponseDto dto : reportsList) {
            org.json.simple.JSONObject jTemp = ObjectMaker.getSimpleJSONObject();
            jTemp.putAll(dto.convertMap());
            jTemp.put("reportedWriter", surveysRepository.findByScode(dto.getS_code()).getEmail());
            jsonArray.add(jTemp);
        }
        jsonObject.put("reports", jsonArray);
        return jsonObject;
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public org.json.simple.JSONObject approve(HttpServletRequest request) {
        org.json.simple.JSONObject jsonObject = ObjectMaker.getSimpleJSONObject();
        int s_code = Integer.parseInt(request.getParameter("s_code"));
        try {
            Reports report = reportsRepository.findByScodeAndReportstate(s_code, ReportState.N);
            report.updateReportState(ReportState.Y);
            jsonObject.put("result", true);
            jsonObject.put("message", "신고 승인 처리가 완료 되었습니다.");
        } catch(Exception exception) {
            jsonObject = ObjectMaker.getJSONObjectWithException(new ReportApproveException());
        }
        return jsonObject;
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public org.json.simple.JSONObject remove(HttpServletRequest request) {
        org.json.simple.JSONObject jsonObject = ObjectMaker.getSimpleJSONObject();
        int s_code = Integer.parseInt(request.getParameter("s_code"));
        try {
            reportsRepository.remove(s_code);
            jsonObject.put("result", true);
            jsonObject.put("message", "신고 반려 처리가 완료 되었습니다.");
        } catch(Exception exception) {
            jsonObject = ObjectMaker.getJSONObjectWithException(new ReportRemoveException());
        }
        return jsonObject;
    }
}
