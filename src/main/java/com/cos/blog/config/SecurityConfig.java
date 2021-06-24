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

    // 1. Bean 어노테이션은 메서드에 붙여서 객체 생성시 사용 (빈 등록)
    @Bean
    public BCryptPasswordEncoder encodePWD() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .anyRequest().authenticated()
            .and()
                .formLogin()
                .loginPage("/auth/loginForm") // 커스텀 로그인 페이지
        ; 
    }
}
