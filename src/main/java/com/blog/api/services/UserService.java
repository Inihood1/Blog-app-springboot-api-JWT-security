package com.blog.api.services;

import com.blog.api.entities.User;
import com.blog.api.payloads.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto user);
    UserDto updateUser(UserDto user, Integer userId);
    UserDto getUserById(Integer userId);
    List<UserDto> getAllUsers ();
    void deleteUser(Integer userId, String email);
}
