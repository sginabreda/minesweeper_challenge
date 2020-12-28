package com.sginabreda.minesweeper.domain.mapper;

import com.sginabreda.minesweeper.delivery.dto.request.UserRequestDto;
import com.sginabreda.minesweeper.delivery.dto.response.UserDto;
import com.sginabreda.minesweeper.domain.entity.User;
import com.sginabreda.minesweeper.infrastructure.repository.model.UserModel;

public class UserMapper {

	public UserDto toDto(User user) {
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setUsername(user.getUsername());
		userDto.setPassword(user.getPassword());

		return userDto;
	}

	public User toDomain(UserRequestDto userRequestDto) {
		User user = new User();
		user.setUsername(userRequestDto.getUsername());
		user.setPassword(userRequestDto.getPassword());

		return user;
	}

	public User toDomain(UserModel userModel) {
		User user = new User();
		user.setId(userModel.getId());
		user.setUsername(userModel.getUsername());
		user.setPassword(userModel.getPassword());

		return user;
	}

	public UserModel toModel(User user) {
		UserModel userModel = new UserModel();
		userModel.setPassword(user.getPassword());
		userModel.setUsername(user.getUsername());

		return userModel;
	}

}
