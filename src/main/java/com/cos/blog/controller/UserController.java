package com.cos.blog.controller;

import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.OAuthToken;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Value("${cos.key}")
    private String cosKey;

    @GetMapping("/auth/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    @GetMapping("/auth/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    @GetMapping("/user/updateForm")
    public String updateForm() {
        return "user/updateForm";
    }

    @GetMapping("/auth/kakao/callback")
//    @ResponseBody
    public String kakaoCallback(String code) {

        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "3bb60d70dee580c716f767af0de9c912");
        params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
        params.add("code", code);

        // HttpHeader ??? HttpBody??? ????????? ??????????????? ??????
        HttpEntity<MultiValueMap<String, String>> kakaoRequest = new HttpEntity<>(params, headers);

        // Http ??????
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oauthToken = null;
        try {
            // json -> java object
            oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // ?????? ??? ?????? ????????? ????????? ???????????? ????????????
        RestTemplate rt2 = new RestTemplate();

        // HttpHeader ???????????? ??????
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + oauthToken.getAccess_token());
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpHeader??? HttpBody??? ????????? ??????????????? ??????
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
                new HttpEntity<>(headers2);

        // Http ???????????? - Post???????????? - ????????? response ????????? ?????? ??????.
        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        // ????????? ?????? ?????? json -> java object
        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // User ???????????? : username, password, email
//        System.out.println("????????? ?????????(??????) : "+kakaoProfile.getId());
//        System.out.println("????????? ????????? : "+kakaoProfile.getKakao_account().getEmail());

        // ?????? ????????? ????????? ????????? ??????????????? ??? ??? ?????? ????????? _id ??? ????????????.
//        System.out.println("??????????????? ???????????? : "+kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId());
//        System.out.println("??????????????? ????????? : "+kakaoProfile.getKakao_account().getEmail());

//        UUID uuid = UUID.randomUUID();
//        String password = uuid.toString();

        User kakaoUser = User.builder()
                .username(kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId())
                .password(cosKey)
                .email(kakaoProfile.getKakao_account().getEmail())
                .oauth("kakao")
                .build();
        
        // ????????? ?????? ???????????? ?????? ??????
        User originUser = userService.findUser(kakaoUser.getUsername());
        if(originUser.getUsername() == null) {
            userService.save(kakaoUser);
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), cosKey));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/";
    }
}
//https://kauth.kakao.com/oauth/token
//        grant_type=authorization_code
//        client_id=3bb60d70dee580c716f767af0de9c912
//        redirect_uri=http://localhost:8000/auth/kakao/callback
//        code=code
