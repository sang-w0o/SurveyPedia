package com.surveypedia.reports.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ReportInsertRequestDto {

    private int s_code;
    private String reporter;
    private String cause;
}
