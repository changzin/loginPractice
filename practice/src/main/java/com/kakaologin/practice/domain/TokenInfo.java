package com.kakaologin.practice.domain;

import lombok.Data;

@Data
public class TokenInfo {
    private long id;
    private int expires_in;
    private int app_id;
    private int expiresInMillis;
    private int appId;
}
