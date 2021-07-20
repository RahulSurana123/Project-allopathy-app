package com.allopathy.management.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.IdGenerator;
import org.springframework.util.JdkIdGenerator;

import com.allopathy.management.config.AppProperties;
import com.allopathy.management.constant.SecurityConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TokenServiceImpl {

	private IdGenerator idGenerator = new JdkIdGenerator();

//	@Autowired
//	private AppProperties properties;
//	
//	
//	public String createAccessToken(String username) {
//		String accessToken = Jwts.builder()
//				.signWith(
//						Keys.hmacShaKeyFor(
//								properties.getJwt().getSecret().getBytes()),
//						SignatureAlgorithm.HS512)
//				.setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
//				.setSubject(username)
//				.setExpiration(new Date(System.currentTimeMillis()
//						+ properties.getJwt().getAccessValidity()))
//				.setIssuer(properties.getJwt().getIssuer())
//				.setAudience(properties.getJwt().getClientId()).compact();
//		return accessToken;
//	}
//	
//	public String generateId() {
//		return idGenerator.generateId().toString();
//	}
//	
//	public Long getExpirationTime(String accessToken) {
//		Long expiryTime = parseClaimsJws(accessToken).getBody().getExpiration()
//				.getTime();
//		return expiryTime;
//	}
//
//	
//	public String getSubject(String accessToken) {
//		String subject = parseClaimsJws(accessToken).getBody().getSubject();
//		return subject;
//	}
//
//	
//	public Jws<Claims> parseClaimsJws(String accessToken) {
//		return Jwts.parser()
//				.setSigningKey(properties.getJwt().getSecret().getBytes())
//				.parseClaimsJws(accessToken);
//	}
}
