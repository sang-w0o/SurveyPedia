package com.surveypedia.reports.dto;

import com.surveypedia.domain.reports.Reports;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

@NoArgsConstructor
@Getter
@Setter
public class ReportResponseDto {

    private int s_code;
    private String reporter;
    private String cause;

    public ReportResponseDto(Reports report) {
        this.s_code = report.getScode();
        this.reporter = report.getReporter();
        this.cause = report.getCause();
    }

    public HashMap<String, Object> convertMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("s_code", s_code);
        map.put("reporter", reporter);
        map.put("cause", cause);
        return map;
    }
}
