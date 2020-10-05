package com.surveypedia.domain.pointhistory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Integer> {

    List<PointHistory> findByEmail(String email);
}
