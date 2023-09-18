package com.blog.api.services;

import com.blog.api.entities.LoginRequest;
import com.blog.api.entities.User;
import com.blog.api.payloads.AuthenticationResponse;
import com.blog.api.payloads.UserDto;


public interface AuthenticationService {
    AuthenticationResponse login(LoginRequest user);

    AuthenticationResponse register(User user);
}
