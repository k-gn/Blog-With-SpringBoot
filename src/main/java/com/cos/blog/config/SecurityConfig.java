package com.cos.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration // 빈 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것 (IoC 관리)
@EnableWebSecurity // 시큐리티 필터 추가 = 활성화 된 시큐리티 설정을 해당 파일에서 하겠다.
@EnableGlobalMethodSecurity(prePostEnabled = true) // 메소드 시큐리티를 사용하기 위한 선언
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // 비밀번호 암호화를 위한 인코더 등록
    // 여담으로 해쉬를 사용해 원본과 위조본을 구분할 수 있다.
    // 1. Bean 어노테이션은 메서드에 붙여서 객체 생성시 사용 (빈 등록)
    @Bean
    public BCryptPasswordEncoder encodePWD() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // csrf 토큰 비활성화 (테스트 시 유용)
            .authorizeRequests()
                .antMatchers("/auth/**", "/js/**", "/css/**", "/image/**").permitAll()
                .anyRequest().authenticated()
            .and()
                .formLogin()
                .loginPage("/auth/loginForm") // 커스텀 로그인 페이지
        ; 
    }

}

// xss = 자바스크립트 공격
// 스크립트를 못쓰도록 lucy 필터 같은 오픈소스를 사용하여 해결할 수 있다.

// csrf = 관리자 권한을 탈취하거나 악용하는 스크립트를 심어두고 사이트 관리자가 해당 링크 또는 페이지 접속 시 동작시키는 공격
// Refferrer 검증 또는 csrf 토큰을 사용해 해결할 수 있다.

// 스프링 시큐리티는 기본적으로 csrf 토큰이 없으면 해당 요청을 막는다.
// 모든 POST 방식의 데이터 전송에 토큰 값이 있어야 한다. GET 방식에는 적용되지 않는다.