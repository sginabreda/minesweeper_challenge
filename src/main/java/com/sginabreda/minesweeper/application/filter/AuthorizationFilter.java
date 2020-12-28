package com.sginabreda.minesweeper.application.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sginabreda.minesweeper.config.context.Header;
import com.sginabreda.minesweeper.delivery.dto.ApiError;
import com.sginabreda.minesweeper.domain.exception.RequestException;
import com.sginabreda.minesweeper.infrastructure.service.UserService;
import com.sginabreda.minesweeper.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class AuthorizationFilter extends HttpFilter {

	private final ObjectMapper mapper;
	private final JwtTokenUtil jwtTokenUtil;
	private final UserService userService;
	private final String BEARER_PREFIX = "Bearer ";
	private final String unauthorizedCode = "unauthorized";
	private final Integer unauthorizedHttpStatus = HttpStatus.UNAUTHORIZED.value();

	public AuthorizationFilter(ObjectMapper mapper, JwtTokenUtil jwtTokenUtil, UserService userService) {
		this.mapper = mapper;
		this.jwtTokenUtil = jwtTokenUtil;
		this.userService = userService;
	}

	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			String authToken = request.getHeader(Header.AUTHORIZATION.label);
			if (authToken.isBlank() || authToken.isEmpty()) {
				setApiErrorResponse(response, "Authorization token cannot be empty or null", "unauthorized",
						HttpStatus.UNAUTHORIZED.value());
			}
			DecodedJWT jwtToken = JWT.decode(authToken.replace(BEARER_PREFIX, ""));
			jwtTokenUtil.validateToken(jwtToken);
			setAuthentication(jwtToken);
			log.info("JWT Token validated");
		}
		catch (RequestException e) {
			log.info(e.getMessage(), e);
			setApiErrorResponse(response, e.getMessage(), e.getCode(), e.getStatus());
			return;
		}
		catch (SignatureVerificationException e) {
			log.info(e.getMessage(), e);
			setApiErrorResponse(response, e.getMessage(), unauthorizedCode, unauthorizedHttpStatus);
			return;
		}
		catch (Exception e) {
			log.info("Authorization token could not be verified", e);
			setApiErrorResponse(response, "Authorization token could not be verified", unauthorizedCode,
					unauthorizedHttpStatus);
			return;
		}
		chain.doFilter(request, response);
	}

	private void setApiErrorResponse(HttpServletResponse response, String message, String code, Integer httpStatus) throws IOException {
		ApiError error = new ApiError(code, message);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(httpStatus);
		response.getWriter().write(mapper.writeValueAsString(error));
	}

	private void setAuthentication(DecodedJWT jwtToken) {
		String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
		UserDetails userDetails = userService.loadUserByUsername(username);
		SecurityContextHolder.getContext().setAuthentication(jwtTokenUtil.getAuthentication(jwtToken, userDetails));
	}
}
