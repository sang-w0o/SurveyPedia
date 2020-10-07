package com.surveypedia.pointhistory.dto;

import com.surveypedia.domain.pointhistory.PointHistory;
import com.surveypedia.domain.pointhistory.PointHistoryType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PointHistoryListDto {

    private int totalPoint;
    private PointHistoryType ph_type;
    private int pointChange;
    private String s_title;

    public PointHistoryListDto(PointHistory pointHistory) {
        this.ph_type = pointHistory.getPhtype();
        this.pointChange = pointHistory.getPointchange();
    }
}
