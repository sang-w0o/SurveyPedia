package com.surveypedia.pointhistory.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PointHistoryPurchaseRequestDto {

    private int s_code;
    private String buyer;
    private String seller;
    private int price;
}
