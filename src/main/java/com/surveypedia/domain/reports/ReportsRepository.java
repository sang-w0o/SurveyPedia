package com.surveypedia.domain.reports;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportsRepository extends JpaRepository<Reports, Integer> {

    Reports findByScodeAndReporter(Integer s_code, String reporter);
}
