package com.cos.blog.auth;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {

    private final UserRepository userRepo;

    // 스프링이 로그인 요청을 가로챈 후 해당 사용자가 DB에 있는지 확인 후 리턴
    // 비밀번호는 알아서 처리해준다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User principal = userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다. : " + username));

        return new PrincipalDetail(principal); // 시큐리티 인증영역에 해당 유저정보가 저장된다.
    }
}
