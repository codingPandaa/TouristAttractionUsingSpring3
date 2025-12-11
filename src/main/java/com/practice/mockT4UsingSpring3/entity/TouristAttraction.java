package com.practice.mockT4UsingSpring3.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TouristAttraction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long attractionId;
	private String name;
	private String location;
	private String description;
	private int overallRating;
	private double entryFee;
	private String openingHours;

}
