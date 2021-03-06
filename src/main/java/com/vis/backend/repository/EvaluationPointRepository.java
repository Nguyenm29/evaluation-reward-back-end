package com.vis.backend.repository;

import com.vis.backend.entity.EvaluationPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EvaluationPointRepository extends JpaRepository<EvaluationPoint, Long> {
    Optional<List<EvaluationPoint>> findAllByEmployeeId(Long employeeId);
}
