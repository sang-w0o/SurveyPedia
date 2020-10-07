package com.surveypedia.domain.choiceresults;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChoiceResultsRepository extends JpaRepository<ChoiceResults, Integer> {

    ChoiceResults findByScodeAndRespondent(Integer s_code, String respondent);
}
