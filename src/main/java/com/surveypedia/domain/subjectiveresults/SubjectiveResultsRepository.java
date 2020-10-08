package com.surveypedia.domain.subjectiveresults;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectiveResultsRepository extends JpaRepository<SubjectiveResults, Integer> {

    List<SubjectiveResults> findByScodeAndRespondent(Integer s_code, String respondent);
}
