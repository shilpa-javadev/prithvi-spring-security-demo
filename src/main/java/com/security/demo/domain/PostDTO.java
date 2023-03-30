package com.security.demo.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class PostDTO {
    private Integer id;

    private String title;

    private String body;

    private String slug;
}
