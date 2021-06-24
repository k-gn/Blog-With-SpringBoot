package com.cos.blog.auth;

import com.cos.blog.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

// 시큐리티 인증 세션은 UserDetails 타입으로 저장되기 때문에 UserDetails 타입의 객체가 필요
// 시큐리티가 로그인 처리를 진행 후 완료되면 UserDetails 타입의 오브젝트를 시큐리티 고유 (인증)세션저장소에 저장을 해준다.
// 커스텀 로그인 처리를 위해 꼭 필요하다. ( 안그러면 기본 계정인 user 로만 로그인이 가능,, )
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PrincipalDetail implements UserDetails {

    private User user;

    // 해당 주체의 권한 리스트
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collectors = new ArrayList<>();
        collectors.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        return collectors;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 계정이 만료되었는지 확인
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 잠긴 계정인지 확인
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호가 만료되었는지 확인
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 사용가능한(활성화된) 계정인지 확인
    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUser(User persistence) {
        this.user = persistence;
    }
}
