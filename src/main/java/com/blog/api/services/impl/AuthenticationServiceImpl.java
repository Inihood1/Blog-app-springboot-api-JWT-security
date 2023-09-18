package com.blog.api.services.impl;


import com.blog.api.confiq.JwtService;
import com.blog.api.entities.LoginRequest;
import com.blog.api.entities.Role;
import com.blog.api.entities.User;
import com.blog.api.exceptions.AuthException;
import com.blog.api.payloads.AuthenticationResponse;
import com.blog.api.payloads.UserDto;
import com.blog.api.repositories.UserRepo;
import com.blog.api.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepo repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    @Override
    public AuthenticationResponse login(LoginRequest request) {
        AuthenticationResponse response = new AuthenticationResponse();
        Optional<User> userDetails = repository.findByEmail(request.getEmail());
        if(userDetails.isEmpty()){
            throw new AuthException("Email not found");
        }else {
            if (!passwordEncoder.matches(request.getPassword(), userDetails.get().getPassword())){
                throw new AuthException("Password do not match");
            }
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            var user =  repository.findByEmail(request.getEmail()).orElseThrow();
            var jwtToken = jwtService.generateToken(user);
            response.setToken(jwtToken);
            response.setMessage("Welcome, " +  userDetails.get().getName());
            return response;
        }
    }

    @Override
    public AuthenticationResponse register(User request) {
        Optional<User> userDetails = repository.findByEmail(request.getEmail());
        if(userDetails.isPresent()) {
            throw new AuthException("Email already exist");
        }
        var user = User.builder()
                .name(request.getName())
                .about(request.getAbout())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ADMIN) // save the role as user by default
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        AuthenticationResponse response = new AuthenticationResponse();
        response.setMessage("Successful");
        response.setToken(jwtToken);
        return response;
    }
}
