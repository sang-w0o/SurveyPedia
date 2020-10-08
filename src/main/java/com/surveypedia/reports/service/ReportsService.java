package com.surveypedia.reports.service;

import com.surveypedia.domain.reports.Reports;
import com.surveypedia.domain.reports.ReportsRepository;
import com.surveypedia.reports.exception.ReportCheckException;
import com.surveypedia.tools.ObjectMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Service
public class ReportsService {

    private final ReportsRepository reportsRepository;

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
}
