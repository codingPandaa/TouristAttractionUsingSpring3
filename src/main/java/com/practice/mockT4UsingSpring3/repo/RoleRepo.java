package com.practice.mockT4UsingSpring3.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.mockT4UsingSpring3.entity.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {

	Optional<Role> findByName(String name);
}
