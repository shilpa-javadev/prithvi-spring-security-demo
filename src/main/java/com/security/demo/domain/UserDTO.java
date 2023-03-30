package com.security.demo.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class UserDTO {
    private Integer id;

    private String username;

    private String email;

    private String phone;
}
