package com.vis.backend.repository;

import com.vis.backend.entity.RewardPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RewardPointRepository extends JpaRepository<RewardPoint, Long> {
    
    @Query(value = "SELECT SUM(rp.point) FROM reward_point rp WHERE rp.employee_id = :employeeId", nativeQuery = true)
    Optional<Float> getRewardPoint(@Param("employeeId") Long employeeId);
}
