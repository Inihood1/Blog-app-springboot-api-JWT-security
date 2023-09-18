package com.blog.api.services.impl;

import com.blog.api.entities.Role;
import com.blog.api.entities.User;
import com.blog.api.exceptions.AuthException;
import com.blog.api.exceptions.ResourceNotFoundException;
import com.blog.api.payloads.UserDto;
import com.blog.api.repositories.UserRepo;
import com.blog.api.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public UserDto createUser(UserDto userdto) {
        User user = dtoToUser(userdto);
        User saveUser = userRepo.save(user);
        return userToDto(saveUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        User updatedUser = userRepo.save(user);
        return userToDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", " Id ", userId));
        return userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> userList = userRepo.findAll();
        return userList.stream().map(this::userToDto).collect(Collectors.toList());
    }

    /*
    * There is a better way to do this
    *
    * */
    @Override
    public void deleteUser(Integer userIdToDelete, String email) {
        User userAdmin = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User", " Id " + email, 0));
       // System.out.println("the details: " + userAdmin.getAuthorities() + userAdmin.getRole());
        if (userAdmin.getRole() == Role.ADMIN){
            User user = userRepo.findById(userIdToDelete).orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userIdToDelete));
            userRepo.delete(user);
        }else {
            throw new AuthException("You not have permission to delete a user!");
        }

    }

    private User dtoToUser(UserDto userDto){
        //User user = new User();
//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setAbout(userDto.getAbout());
//        user.setPassword(userDto.getPassword());
        return modelMapper.map(userDto, User.class);
    }

    public UserDto userToDto(User user){
        //UserDto userDto = new UserDto();
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
//        userDto.setAbout(user.getAbout());
        return modelMapper.map(user, UserDto.class);
    }
}














