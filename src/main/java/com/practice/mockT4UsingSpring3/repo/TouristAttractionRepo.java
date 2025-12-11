package com.practice.mockT4UsingSpring3.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.mockT4UsingSpring3.entity.TouristAttraction;


public interface TouristAttractionRepo extends JpaRepository<TouristAttraction, Long> {

	List<TouristAttraction> findByNameContainsIgnoreCaseOrDescriptionContainingIgnoreCaseOrLocationContainingIgnoreCase(
			String name, String description, String location);

}
