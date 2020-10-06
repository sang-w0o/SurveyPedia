package com.surveypedia.domain.pointhistory;

import com.surveypedia.tools.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Integer> {

    List<PointHistory> findByEmail(String email);

    @Query(value = SQL.Survey.SURVEY_PARTICIPATE_COUNT, nativeQuery = true)
    Integer getParticipateCount(String email);
}
