package com.surveypedia.pointhistory.dto;

import com.surveypedia.domain.pointhistory.PointHistory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PointHistoryListDto {

    private int totalPoint;
    private String ph_type;
    private int pointChange;
    private String s_title;

    public PointHistoryListDto(PointHistory pointHistory) {
        this.ph_type = pointHistory.getPh_type();
        this.pointChange = pointHistory.getPointchange();

    }
}
