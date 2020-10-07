package com.surveypedia.domain.pointhistory;

import com.surveypedia.tools.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Integer>, JpaSpecificationExecutor<PointHistory> {

    List<PointHistory> findByEmail(String email);

    @Query(value = SQL.Survey.SURVEY_PARTICIPATE_COUNT, nativeQuery = true)
    Integer getParticipateCount(String email);

    PointHistory findByEmailAndScodeAndPhtype(String email, Integer s_code, PointHistoryType ph_type);

    @Query(value = SQL.PointHistory.GET_POINT_BY_EMAIL, nativeQuery = true)
    Integer getPoint(String email);
}
