package com.kakaologin.practice.domain;

import lombok.Data;

@Data
public class TokenDto {
    private String token_type;
    private String access_token;
    private String id_token;
    private int expires_in;
    private String refresh_token;
    private int refresh_token_expires_in;
    private String scope;
}
