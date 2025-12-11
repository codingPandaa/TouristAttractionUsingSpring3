package com.practice.mockT4UsingSpring3.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.mockT4UsingSpring3.dto.RegisterRequest;
import com.practice.mockT4UsingSpring3.entity.Role;
import com.practice.mockT4UsingSpring3.entity.UserEntity;
import com.practice.mockT4UsingSpring3.repo.RoleRepo;
import com.practice.mockT4UsingSpring3.repo.UserRepo;
import com.practice.mockT4UsingSpring3.utility.JwtUtil;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

	private AuthenticationManager authenticationManager;

	private UserRepo repo;

	private RoleRepo roleRepo;

	private PasswordEncoder passwordEncoder;

	private JwtUtil jwtUtil;

	private UserDetailsService userDetailsService;

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequest req) {

		if (repo.findByUsername(req.getUsername()).isPresent()) {
			return ResponseEntity.badRequest().body(Map.of("error", "Username already exists"));
		}

		Role userRole = roleRepo.findByName("ROLE_USER").orElseThrow(() -> new RuntimeException("ROLE_USER not found"));

		UserEntity user = new UserEntity();
		user.setUsername(req.getUsername());
		user.setPassword(passwordEncoder.encode(req.getPassword()));
		user.getRoles().add(userRole); // ← assign default ROLE_USER

		repo.save(user);

		return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("msg", "User registered successfully"));
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserEntity user) {

		try {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		} catch (BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(Map.of("error", "invalid username and password"));
		}

		UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());

		String token = jwtUtil.generateToken(userDetails);

		return ResponseEntity.status(HttpStatus.OK).body(Map.of("accessToken", token));
	}
}
