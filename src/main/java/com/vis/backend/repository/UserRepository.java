package com.vis.backend.repository;

import com.vis.backend.entity.RewardPoint;
import com.vis.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUserNameAndPassword(String username, String password);

}
