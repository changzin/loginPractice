package com.kakaologin.practice.controller;

import com.kakaologin.practice.domain.KakaoSignupDto;
import com.kakaologin.practice.service.KakaoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class KakaoController {

    @Autowired
    private KakaoService kakaoService;

    @GetMapping("/login")
    public RedirectView kakaoLogin(){
        return kakaoService.kakaoOAuth();
    }


    // 카카오 인증이나 회원가입 후에 콜백이 되는 URL이다. 쿠키 인증 같은 것을 하면 좋을 것 같다.
    @GetMapping("/login-callback")
    public String kakaoCallback(HttpServletRequest request, Model model){
        String code = request.getParameter("code");
        if (code == null){
            return "error";
        }
        Long kakaoId = kakaoService.loginCallBack(code);
        model.addAttribute("kakaoId", kakaoId);
        return "kakaoSignUp";
    }

    @PostMapping("/signUpWithKakao")
    public String signUpWithKakao(@RequestParam KakaoSignupDto kakaoSignupDto){

        return "main";
    }
}
