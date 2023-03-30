package com.security.demo.controllers;

import com.security.demo.domain.UserDTO;
import com.security.demo.domain.UserLoginRequest;
import com.security.demo.domain.UserLoginResponse;
import com.security.demo.domain.UserRequest;
import com.security.demo.repo.UserRepo;
import com.security.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("profiles/api/v1")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("User controller is UP");
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserRequest userRequest){
        UserDTO user = userService.registerUser(userRequest);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest){
        UserLoginResponse userLoginResponse = userService.loginUser(userLoginRequest);
        return ResponseEntity.ok(userLoginResponse);
    }

}
