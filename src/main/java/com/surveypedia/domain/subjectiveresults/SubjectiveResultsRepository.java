package com.surveypedia.domain.subjectiveresults;

import com.surveypedia.tools.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubjectiveResultsRepository extends JpaRepository<SubjectiveResults, Integer> {

    List<SubjectiveResults> findByScodeAndRespondent(Integer s_code, String respondent);

    @Query(value = SQL.SubjectiveResults.GET_ANSWERS_BY_S_CODE_AND_Q_NUMBER, nativeQuery = true)
    List<String> getAnswers(Integer s_code, Integer q_number);
}
