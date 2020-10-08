package com.surveypedia.domain.choiceresults;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChoiceResultsRepository extends JpaRepository<ChoiceResults, Integer> {

    List<ChoiceResults> findByScodeAndRespondent(Integer s_code, String respondent);
}
