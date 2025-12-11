package com.practice.mockT4UsingSpring3.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.practice.mockT4UsingSpring3.dto.RatingUpdateRequest;
import com.practice.mockT4UsingSpring3.entity.TouristAttraction;
import com.practice.mockT4UsingSpring3.service.TouristAttractionService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/tourist")
@AllArgsConstructor
public class TouristAttractionController {

	private TouristAttractionService service;

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/add")
	public ResponseEntity<?> addAttraction(@RequestBody TouristAttraction attraction) {

		return service.addAttraction(attraction);
	}

	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/list")
	public ResponseEntity<?> getAllAttraction() {

		return service.getAllAttraction();
	}

	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/search")
	public ResponseEntity<?> searchUsingQuery(@RequestParam("query") String query) {

		return service.searchUsingQuery(query);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/batch-update-ratings")
	public ResponseEntity<?> batchUpdateRatings(@RequestBody List<RatingUpdateRequest> list) {

		return service.batchUpdateRatings(list);
	}
}
