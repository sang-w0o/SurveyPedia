package com.surveypedia.domain.reports;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportsRepository extends JpaRepository<Reports, Integer> {

    Reports findByScodeAndReporter(Integer s_code, String reporter);
    List<Reports> findAll();
    Reports findByScode(Integer s_code);
}
