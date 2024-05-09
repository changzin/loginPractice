package com.kakaologin.practice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaologin.practice.domain.TokenDto;
import com.kakaologin.practice.domain.TokenInfo;
import com.kakaologin.practice.util.Config;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

@Service
public class KakaoService {
    @Autowired
    HttpCallService httpCallService;
    @Autowired
    Config config;

    public RedirectView kakaoOAuth(){
        String uri = config.getAUTHORIZE_URI()+"?redirect_uri="+config.getREDIRECT_URI()+"&response_type=code&client_id="+config.getREST_API_KEY();
        return new RedirectView(uri);
    }

    public Long loginCallBack(String code) {
        String param = "grant_type=authorization_code" + "&client_id=" + config.getREST_API_KEY() + "&redirect_uri=" + config.getREDIRECT_URI() + "&code=" + code;
        String data = httpCallService.call("POST", config.getTOKEN_URI(), "", param);
        ObjectMapper objectMapper = null;
        TokenDto tokenDto = null;
        try {
            objectMapper = new ObjectMapper();
            tokenDto = new TokenDto();
            tokenDto = objectMapper.readValue(data, TokenDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Long kakaoId = getKakaoUserId(tokenDto.getAccess_token());
        return kakaoId;
    }

    public Long getKakaoUserId(String accessToken){
        ObjectMapper objectMapper = null;
        TokenInfo tokenInfo = null;
        tokenInfo = new TokenInfo();
        String data = httpCallService.call("GET", config.getTOKEN_INFO_URI(), "Bearer " + accessToken, null);
        try {
            objectMapper = new ObjectMapper();
            tokenInfo = new TokenInfo();
            tokenInfo = objectMapper.readValue(data, TokenInfo.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return tokenInfo.getId();
    }
}
