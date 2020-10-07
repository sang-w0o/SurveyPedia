package com.surveypedia.domain.subjectiveresults;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectiveResultsRepository extends JpaRepository<SubjectiveResults, Integer> {

    SubjectiveResults findByScodeAndRespondent(Integer s_code, String respondent);
}
