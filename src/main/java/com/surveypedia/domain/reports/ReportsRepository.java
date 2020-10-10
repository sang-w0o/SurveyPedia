package com.surveypedia.domain.reports;

import com.surveypedia.tools.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReportsRepository extends JpaRepository<Reports, Integer> {

    Reports findByScodeAndReporter(Integer s_code, String reporter);
    List<Reports> findByReportstate(ReportState state);
    Reports findByScodeAndReportstate(Integer s_code, ReportState state);

    @Transactional
    @Modifying
    @Query(value = SQL.Reports.REMOVE_REPORT_BY_S_CODE, nativeQuery = true)
    void remove(Integer s_code);

    @Query(value = SQL.Reports.GET_ALL_REPORTED_S_CODE, nativeQuery = true)
    List<Integer> getAllReportedS_Codes();

}
