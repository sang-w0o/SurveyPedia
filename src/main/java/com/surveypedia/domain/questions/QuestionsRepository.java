package com.surveypedia.domain.questions;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionsRepository extends JpaRepository<Questions, Integer> {

    List<Questions> findByScode(Integer s_code);
}
