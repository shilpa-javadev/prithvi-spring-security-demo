package com.security.demo.services;

import com.security.demo.domain.UserDTO;
import com.security.demo.domain.UserLoginRequest;
import com.security.demo.domain.UserLoginResponse;
import com.security.demo.domain.UserRequest;
import com.security.demo.model.Role;
import com.security.demo.model.Users;
import com.security.demo.repo.UserRepo;
import com.security.demo.security.MyUserDetails;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Set;

//Don't get confused with UserDetailsService/UserDetailsServiceImpl. THis is our regular service class for userservice.
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepo userRepo;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    public UserDTO registerUser(UserRequest userRequest){
        Users existingUser = userRepo.findUserByUsername(userRequest.getUsername());
        if(existingUser != null)
            try {
                throw new Exception("User already exists with this username");
            } catch (Exception e) {
                throw new RuntimeException("User already exists with this username");
            }
        Set<Role> roles = new HashSet<>();
        Users user = Users.builder()
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .username(userRequest.getUsername())
                .phone(userRequest.getPhone())
                .build();
        if(StringUtils.isNotEmpty(userRequest.getRole())){
            roles.add(Role.builder().role(userRequest.getRole()).build());
        }else{
            roles.add(Role.builder().role("USER").build());
        }
        user.setRoles(roles);
        Users savedUser = userRepo.saveAndFlush(user);
        return UserDTO.builder()
                .id(savedUser.getId())
                .email(savedUser.getEmail())
                .phone(savedUser.getPhone())
                .email(savedUser.getEmail())
                .username(savedUser.getUsername())
                .build();
    }

    @Override
    public UserLoginResponse loginUser(UserLoginRequest userLoginRequest) {
        UserDetails userDetails = this.loadUserByUsername(userLoginRequest.getUsername());
        //if(userDetails.getPassword().equals(passwordEncoder.encode(userLoginRequest.getPassword()))){
           return UserLoginResponse.builder()
                    .username(userDetails.getUsername())
                    .signedIn(StringUtils.isNotEmpty(userDetails.getUsername())?true:false)
                    .build();
       // }else
        //return UserLoginResponse.builder().build();
    }


    //we need to override the loadUserByUsername method provided by UserDetailsService(extended by UserService now)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepo.findUserByUsername(username);
        if(user == null){
            throw new EntityNotFoundException("user not found in Database");
        }
        UserDetails userDetails = new MyUserDetails(user);
        return userDetails;
    }



}
