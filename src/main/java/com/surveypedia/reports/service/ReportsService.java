package com.surveypedia.reports.service;

import com.surveypedia.domain.reports.ReportState;
import com.surveypedia.domain.reports.Reports;
import com.surveypedia.domain.reports.ReportsRepository;
import com.surveypedia.reports.dto.ReportInsertRequestDto;
import com.surveypedia.reports.exception.ReportCheckException;
import com.surveypedia.reports.exception.ReportInsertException;
import com.surveypedia.tools.ObjectMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Service
public class ReportsService {

    private final ReportsRepository reportsRepository;

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
            exception.printStackTrace();
            jsonObject = ObjectMaker.getJSONObjectWithException(new ReportInsertException());
        }
        return jsonObject;
    }
}
