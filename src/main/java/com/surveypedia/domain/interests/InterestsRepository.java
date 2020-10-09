package com.surveypedia.domain.interests;

import com.surveypedia.tools.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InterestsRepository extends JpaRepository<Interests, String> {

    Interests findByEmailAndScode(String email, Integer s_code);

    @Query(value = SQL.Interest.GET_COUNT_BY_S_CODE, nativeQuery = true)
    Integer getInterestCounts(Integer s_code);

    @Query(value = SQL.Interest.TOTAL_COUNT_OF_INTERESTS_BY_EMAIL, nativeQuery = true)
    Integer getInterestCountsByEmail(String email);

    @Query(value = SQL.Interest.TOTAL_COUNT_OF_ENDED_INTEREST_SURVEYS, nativeQuery = true)
    Integer getInterestCountsOfEndedSurveyByEmail(String email);

}
