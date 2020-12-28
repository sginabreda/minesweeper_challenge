package com.sginabreda.minesweeper.domain.usecase;

import com.sginabreda.minesweeper.delivery.dto.request.UserRequestDto;
import com.sginabreda.minesweeper.delivery.dto.response.JwtTokenDto;
import com.sginabreda.minesweeper.domain.entity.User;
import com.sginabreda.minesweeper.domain.exception.RequestException;
import com.sginabreda.minesweeper.domain.mapper.UserMapper;
import com.sginabreda.minesweeper.infrastructure.service.UserService;
import com.sginabreda.minesweeper.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;

@Slf4j
public class GenerateToken {

	private final UserService userService;
	private final UserMapper userMapper;
	private final JwtTokenUtil jwtTokenUtil;

	public GenerateToken(UserService userService, UserMapper userMapper, JwtTokenUtil jwtTokenUtil) {
		this.userService = userService;
		this.userMapper = userMapper;
		this.jwtTokenUtil = jwtTokenUtil;
	}

	public JwtTokenDto invoke(UserRequestDto userRequestDto) throws RequestException {
		User user = userMapper.toDomain(userRequestDto);
		userService.authenticate(user);
		UserDetails userDetails = userService.loadUserByUsername(user.getUsername());
		String token = jwtTokenUtil.generateToken(userDetails);

		return new JwtTokenDto(token);
	}
}
