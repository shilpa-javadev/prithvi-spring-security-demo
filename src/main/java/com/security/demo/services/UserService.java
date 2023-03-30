package com.security.demo.services;

import com.security.demo.domain.UserDTO;
import com.security.demo.domain.UserLoginRequest;
import com.security.demo.domain.UserLoginResponse;
import com.security.demo.domain.UserRequest;
import com.security.demo.model.Role;
import com.security.demo.model.Users;
import com.security.demo.repo.UserRepo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

public interface UserService extends UserDetailsService {
    public UserDTO registerUser(UserRequest userRequest);

    public UserLoginResponse loginUser(UserLoginRequest userLoginRequest);
}
