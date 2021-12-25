package com.vis.backend.repository;

import com.vis.backend.entity.EvaluationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationHistoryRepository extends JpaRepository<EvaluationHistory, Long> {
}
