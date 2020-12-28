package com.sginabreda.minesweeper.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sginabreda.minesweeper.config.SecurityRole;
import com.sginabreda.minesweeper.domain.exception.RequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Component
public class JwtTokenUtil {

	@Value("${jwt.secret}")
	private String secret;
	private final String ROLE_CLAIM = "role";
	private final int TOKEN_VALIDITY = 5 * 60 * 60;

	public void validateToken(DecodedJWT jwtToken) throws RequestException {
		Algorithm algorithm = Algorithm.HMAC512(secret);
		algorithm.verify(jwtToken);

		if (isTokenExpired(jwtToken)) {
			throw new RequestException("JWT Token provided has expired", "expired.token",
					HttpStatus.UNAUTHORIZED.value());
		}
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userDetails);
	}

	private String doGenerateToken(Map<String, Object> claims, UserDetails userDetails) {
		return JWT.create().withClaim(ROLE_CLAIM, SecurityRole.PLAYER.name())
				.withIssuedAt(new Date(System.currentTimeMillis()))
				.withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
				.withSubject(userDetails.getUsername())
				.sign(Algorithm.HMAC512(secret));
	}

	private Map<String, Claim> getAllClaimsFromToken(DecodedJWT token) {
		return token.getClaims();
	}

	public UsernamePasswordAuthenticationToken getAuthentication(DecodedJWT jwtToken, UserDetails userDetails) {
		Map<String, Claim> claims = getAllClaimsFromToken(jwtToken);
		List<SimpleGrantedAuthority> authorities = Collections.singletonList(
				new SimpleGrantedAuthority(claims.get(ROLE_CLAIM).asString()));

		return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
	}

	private boolean isTokenExpired(DecodedJWT jwtToken) {
		return jwtToken.getExpiresAt().before(Calendar.getInstance().getTime());
	}

	public String getUsernameFromToken(DecodedJWT jwtToken) {
		return jwtToken.getSubject();
	}
}
