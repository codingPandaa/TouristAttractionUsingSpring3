package com.practice.mockT4UsingSpring3.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.practice.mockT4UsingSpring3.dto.RatingUpdateRequest;
import com.practice.mockT4UsingSpring3.entity.TouristAttraction;
import com.practice.mockT4UsingSpring3.repo.TouristAttractionRepo;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TouristAttractionService {

	private TouristAttractionRepo repo;

	public ResponseEntity<?> addAttraction(@RequestBody TouristAttraction attraction) {

		try {

			TouristAttraction save = repo.save(attraction);
			return ResponseEntity.status(HttpStatus.CREATED).body(save);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

	}

	public ResponseEntity<?> getAllAttraction() {

		List<TouristAttraction> list = repo.findAll();

		if (list == null || list.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "list is empty or null"));
		}

		return ResponseEntity.status(HttpStatus.OK).body(Map.of("touristAttractionList", list));

	}

	public ResponseEntity<?> searchUsingQuery(@RequestParam("query") String query) {

		List<TouristAttraction> list = repo
				.findByNameContainsIgnoreCaseOrDescriptionContainingIgnoreCaseOrLocationContainingIgnoreCase(query,
						query, query);

		if (list == null || list.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(Map.of("error", "No matching tourist attraction spots found"));
		}

		return ResponseEntity.status(HttpStatus.OK).body(Map.of("touristAttractionList", list));

	}

	@Transactional
	public ResponseEntity<?> batchUpdateRatings(@RequestBody List<RatingUpdateRequest> list) {

		ArrayList<TouristAttraction> result = new ArrayList<>();

		for (RatingUpdateRequest element : list) {

			Optional<TouristAttraction> optional = repo.findById(element.getAttractionId());

			if (!optional.isPresent()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Attraction not found for ID: " + element.getAttractionId());
			}

			TouristAttraction attraction = optional.get();

			attraction.setOverallRating(element.getNewRating());

			result.add(repo.save(attraction));
		}

		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

}
