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

    @Query(value = SQL.Survey.SURVEY_LAST_S_ID, nativeQuery = true)
    Integer getLastS_id(String email);

    @Query(value = SQL.SurveyInfo.SURVEY_ENDED_LIST, nativeQuery = true)
    List<Object[]> getEndedSurveyLists();

    Survey findByEmailAndScode(String email, Integer s_code);

    @Query(value = SQL.SurveyInfo.SURVEY_COUNT_BY_CATEGORY, nativeQuery = true)
    Integer getTotalCountByCategory(String c_code);

    @Query(value = SQL.SurveyInfo.SURVEY_LIST_BY_CATEGORY, nativeQuery = true)
    List<Object[]> getSurveyListByCategoryAndPage(String c_code, Integer startPos, Integer pageSize);

    Survey findByScode(Integer s_code);
}
