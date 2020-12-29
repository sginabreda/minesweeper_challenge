package com.sginabreda.minesweeper.infrastructure.service;

import com.sginabreda.minesweeper.config.SecurityRole;
import com.sginabreda.minesweeper.domain.entity.User;
import com.sginabreda.minesweeper.domain.exception.RequestException;
import com.sginabreda.minesweeper.infrastructure.repository.UserRepository;
import com.sginabreda.minesweeper.infrastructure.repository.model.UserModel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Slf4j
@Service
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;
	private final AuthenticationManager authenticationManager;

	public UserService(UserRepository userRepository, AuthenticationManager authenticationManager) {
		this.userRepository = userRepository;
		this.authenticationManager = authenticationManager;
	}

	public UserModel save(UserModel userModel) {
		return userRepository.save(userModel);
	}

	public Optional<UserModel> findByUserName(String username) {
		return userRepository.findByUsername(username);
	}

	@SneakyThrows
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (username.isEmpty() || username.isBlank()) {
			throw new RequestException("Username was not provided", "no.username", HttpStatus.BAD_REQUEST.value());
		}
		Optional<UserModel> user = userRepository.findByUsername(username);
		if (user.isEmpty()) {
			throw new RequestException("Username not found", "username.not.found", HttpStatus.NOT_FOUND.value());
		}
		return new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword(),
				Collections.singletonList(new SimpleGrantedAuthority(SecurityRole.PLAYER.name())));
	}

	public void authenticate(User user) throws RequestException {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		}
		catch (BadCredentialsException e) {
			log.info("Invalid credentials", e);
			throw new RequestException("Invalid credentials", "invalid.credentials", HttpStatus.UNAUTHORIZED.value());
		}
		catch (DisabledException e) {
			log.info("Disabled user", e);
			throw new RequestException("Disabled user", "disabled.user", HttpStatus.UNAUTHORIZED.value());
		}
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder, PasswordEncoder passwordEncoder) throws Exception {
		authenticationManagerBuilder.userDetailsService(this).passwordEncoder(passwordEncoder);
	}
}
