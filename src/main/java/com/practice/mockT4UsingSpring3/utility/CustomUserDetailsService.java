package com.practice.mockT4UsingSpring3.utility;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.practice.mockT4UsingSpring3.entity.UserEntity;
import com.practice.mockT4UsingSpring3.repo.UserRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private UserRepo repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserEntity user = repo.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("user not found: " + username));

		List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());

		User userDetails = new User(user.getUsername(), user.getPassword(), authorities);

		return userDetails;
	}

}
