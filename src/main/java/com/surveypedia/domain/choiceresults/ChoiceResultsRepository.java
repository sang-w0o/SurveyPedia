package com.surveypedia.domain.choiceresults;

import com.surveypedia.tools.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChoiceResultsRepository extends JpaRepository<ChoiceResults, Integer> {

    List<ChoiceResults> findByScodeAndRespondent(Integer s_code, String respondent);

    @Query(value = SQL.ChoiceResults.GET_CHOICE_COUNTS_BY_S_CODE_AND_Q_NUMBER_AND_CHOICE_NUM, nativeQuery = true)
    Integer getChoiceCounts(Integer s_code, Integer q_number, Integer choice_num);
}
