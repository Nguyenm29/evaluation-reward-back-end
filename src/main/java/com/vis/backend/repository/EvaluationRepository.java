package com.vis.backend.repository;

import com.vis.backend.entity.Evaluation;
import com.vis.backend.entity.EvaluationPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
}
