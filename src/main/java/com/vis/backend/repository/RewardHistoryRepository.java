package com.vis.backend.repository;

import com.vis.backend.entity.RewardHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RewardHistoryRepository extends JpaRepository<RewardHistory, Long> {
}
