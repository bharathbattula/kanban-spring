package com.kanban.api.config.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

	private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);

	@Value("${jwt.secret}")
	private String secreteKey;

	@Value("${jwt.expiration}")
	private String expiration;

	public JwtTokenProvider() {
		try {
			Long.parseLong(this.expiration);
		} catch (final NumberFormatException e) {
			this.expiration = "3600000";
		}
	}

	public String generateToken(final Authentication authentication) {
		final UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();


		return Jwts.builder()
				.setSubject(String.valueOf(userPrincipal.getId()))
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + Long.parseLong(this.expiration)))
				.signWith(SignatureAlgorithm.HS512, this.secreteKey)
				.compact();

	}

	public Long getUserIdFromToken(final String token) {
		final Claims claims = Jwts.parser()
				.setSigningKey(this.secreteKey)
				.parseClaimsJws(token)
				.getBody();

		return Long.parseLong(claims.getSubject());
	}

	public boolean validateToken(final String token) {
		try {
			Jwts.parser().setSigningKey(this.secreteKey).parseClaimsJws(token);
			return true;
		} catch (final SignatureException e) {
			LOGGER.error("Error parsing token", e);
		} catch (final MalformedJwtException e) {
			LOGGER.error("Token Invalid", e);
		} catch (final ExpiredJwtException e) {
			LOGGER.error("Token expired", e);
		} catch (final UnsupportedJwtException e) {
			LOGGER.error("Token invalid or failed to parse", e);
		} catch (final IllegalArgumentException e) {
			LOGGER.error("Token invalid", e);
		}
		return false;
	}

}
