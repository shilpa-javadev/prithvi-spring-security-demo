package com.security.demo.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class UserRequest {
    private String username;

    private String password;

    private String email;

    private String phone;

    private String role;
}
