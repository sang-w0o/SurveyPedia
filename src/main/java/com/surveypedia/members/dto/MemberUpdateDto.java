package com.surveypedia.members.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MemberUpdateDto {

    private String email;
    private int reported_count;
    private int participated_count;
    private String g_name;
    private int currentPoint;
    private int addPoint;

    public MemberUpdateDto(String email) {
        this.email = email;
        this.reported_count = 0;
        this.participated_count = 0;
    }

    public void addReportedCount() {
        this.reported_count += 1;
    }
}
