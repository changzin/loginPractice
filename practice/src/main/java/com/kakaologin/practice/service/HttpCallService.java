package com.kakaologin.practice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaologin.practice.domain.TokenDto;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@Service
public class HttpCallService {

    public String call(String method, String uri, String header, String param) {
        String result = "";
        try{
            String response = "";
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setRequestProperty("Authorization", header);
            if (param != null){
                System.out.println("param : " + param);
                conn.setDoOutput(true);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
                bw.write(param);
                bw.flush();
            }
            int responseCode = conn.getResponseCode();
            System.out.println("url : " + url);
            System.out.println("responseCode : " + responseCode);
            System.out.println("method : " + method);
            System.out.println("Authorization = " + header);

            // error 있으면 출력
            InputStream stream = conn.getErrorStream();
            if (stream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(stream);
                BufferedReader br = new BufferedReader(inputStreamReader);
                String str;
                while((str = br.readLine()) != null){
                    response += str;
                }
                System.out.println("error response : " + response);
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            while((line=br.readLine()) != null){
                result += line;
            }
            System.out.println("responseBody : " + result);
            br.close();
        } catch(IOException e){
            e.printStackTrace();;
        }
        return result;
    }
}
