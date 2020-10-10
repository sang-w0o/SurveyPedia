package com.surveypedia.domain.choices;

import com.surveypedia.tools.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChoicesRepository extends JpaRepository<Choices, Integer> {

    List<Choices> findByScodeAndQnumber(Integer s_code, Integer q_number);

    @Query(value = SQL.Choices.GET_CHOICE_NUMBERS_BY_S_CODE_AND_Q_NUMBER, nativeQuery = true)
    List<Integer> getChoiceNumbers(Integer s_code, Integer q_number);

    Choices findByScodeAndQnumberAndChoicenum(Integer s_code, Integer q_number, Integer choice_num);
}
