package com.practice.mockT4UsingSpring3.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.mockT4UsingSpring3.entity.UserEntity;



public interface UserRepo extends JpaRepository<UserEntity, Long> {

	Optional<UserEntity> findByUsername(String username);
}
