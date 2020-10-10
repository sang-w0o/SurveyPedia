package com.surveypedia.domain.reports;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Reports {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "r_id")
    private Integer rid;

    @Column(nullable = false, name = "s_code")
    private Integer scode;

    @Column(nullable = false)
    private String reporter;

    @Column(nullable = false)
    private String cause;

    @Column(nullable = false, name = "r_type")
    @Enumerated(EnumType.STRING)
    private ReportType rtype;

    @Column(nullable = false, name = "r_state")
    @Enumerated(EnumType.STRING)
    private ReportState reportstate;

    @Builder
    public Reports(Integer scode, String reporter, String cause, String rtype, ReportState reportstate) {
        this.scode = scode;
        this.reporter = reporter;
        this.cause = cause;
        this.rtype = rtype.equals("W") ? ReportType.W : ReportType.R;
        this.reportstate = reportstate;
    }

    public void updateReportState(ReportState reportState) {
        this.reportstate = reportState;
    }
}
