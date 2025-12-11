package com.practice.mockT4UsingSpring3.utility;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String SECRET_KEY;

	@Value("${jwt.expiration}")
	private long EXPIRATION_TIME;

	private SecretKey getKey() {

		SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
		return key;
	}

	public String generateToken(UserDetails userDetails) {

		Date now = new Date();

		Date expiry = new Date(now.getTime() + EXPIRATION_TIME);

		String token = Jwts.builder().setSubject(userDetails.getUsername()).setIssuedAt(now).setExpiration(expiry)
				.signWith(getKey()).compact();

		return token;

	}

	public String extractUsername(String token) {

		String username = extractAllClaims(token).getSubject();

		return username;
	}

	public boolean validateToken(String token, UserDetails userDetails) {

		return extractUsername(token).equals(userDetails.getUsername())
				&& !extractAllClaims(token).getExpiration().before(new Date());
	}

	private Claims extractAllClaims(String token) {

		Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token);

		Claims payload = jws.getBody();

		return payload;
	}

}
