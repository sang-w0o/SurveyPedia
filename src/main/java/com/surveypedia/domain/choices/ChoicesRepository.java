package com.surveypedia.domain.choices;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChoicesRepository extends JpaRepository<Choices, Integer> {

    List<Choices> findByScodeAndQnumber(Integer s_code, Integer q_number);
}
