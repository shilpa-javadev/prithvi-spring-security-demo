package com.security.demo.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class UserLoginResponse {
    private String username;
    private boolean signedIn;
}



