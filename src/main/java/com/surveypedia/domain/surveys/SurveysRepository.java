package com.surveypedia.domain.surveys;

import com.surveypedia.tools.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SurveysRepository extends JpaRepository<Survey, Integer> {

    @Query(value = SQL.SurveyInfo.SURVEY_INFO_BY_DEAD_LINE, nativeQuery = true)
    List<Object[]> getSurveyInfoByDeadLine();

    @Query(value = SQL.SurveyInfo.SURVEY_INFO_BY_END_SURVEY, nativeQuery = true)
    List<Object[]> getSurveyInfoByEnd();

    @Query(value = SQL.SurveyInfo.SURVEY_INFO_BY_SPARE_SAMPLE_NUM, nativeQuery = true)
    List<Object[]> getSurveyInfoBySpareSampleNum();

    @Query(value = SQL.Survey.SURVEY_HISTORY_BY_CODE, nativeQuery = true)
    List<Object[]> getSurveyInfoByS_code(int s_code);

    @Query(value = SQL.Survey.SURVEY_WRITE_COUNT, nativeQuery = true)
    Integer getWriteCount(String email);

    @Query(value = SQL.Survey.SURVEY_PARTICIPATE_COUNT, nativeQuery = true)
    Integer getParticpateCount(String email);
}
