package com.kakaologin.practice.domain;

import lombok.Data;

@Data
public class User {
    private Long id;
    private Long kakaoId;
    private String nickName;
    private String password;
    private String email;
}
