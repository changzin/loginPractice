package com.kakaologin.practice.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Map;


@Getter
@Component
public class Config {
    private String REDIRECT_URI;
    private String AUTHORIZE_URI;
    private String REST_API_KEY;
    private String TOKEN_URI;
    private String TOKEN_INFO_URI;

    @PostConstruct
    private void init() {
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            ClassPathResource resource = new ClassPathResource("account.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));String str = null;
            String result = "";
            while ((str = br.readLine()) != null){
                result += str;
            }
            Map map = objectMapper.readValue(result, Map.class);
            REDIRECT_URI = (String)map.get("REDIRECT_URI");
            AUTHORIZE_URI = (String)map.get("AUTHORIZE_URI");
            REST_API_KEY = (String)map.get("REST_API_KEY");
            TOKEN_URI = (String)map.get("TOKEN_URI");
            TOKEN_INFO_URI = (String)map.get("TOKEN_INFO_URI");
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
