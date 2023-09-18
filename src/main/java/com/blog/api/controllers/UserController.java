package com.blog.api.controllers;

import com.blog.api.payloads.ApiResponse;
import com.blog.api.payloads.UserDto;
import com.blog.api.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;

//    @PostMapping("/create")
//    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
//       UserDto createdUser =  userService.createUser(userDto);
//       return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
//    }

    @PutMapping("/update{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer uid){
        UserDto updatedUser = userService.updateUser(userDto, uid);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("del/{userId}/{email}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uid, @PathVariable("email") String email){
        userService.deleteUser(uid, email);
        return new ResponseEntity<>(new ApiResponse("User deleted successfully", true), HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/get{userId}")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId){
        return ResponseEntity.ok(userService.getUserById(userId));
    }
}












