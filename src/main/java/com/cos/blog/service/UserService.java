package com.cos.blog.service;

import com.cos.blog.auth.PrincipalDetail;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// service 가 필요한 이유
// 1. 트랜잭션 관리 (트랜잭션 : 일이 처리되기 위한 가장 작은 단위)
// 2. 하나의 기능으로 처리 (서비스화, 여러 기능이 하나로 모인게 하나의 기능으로 처리될 수도 있다.)
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final BCryptPasswordEncoder encoder;

    @Transactional // 데이터 변경에 안정성 증가 + 정합성 유지를 위해서도 사용한다. (같은 트랜잭션 내에서는 동일한 데이터를 볼 수 있도록 보장된다. - REPEACTABLE READ)
    public void save(User user) {
        String encPwd = encoder.encode(user.getPassword());
        user.setPassword(encPwd);
        user.setRole(RoleType.USER);
        userRepo.save(user);
    }

    @Transactional
    public User update(Long id, User reqUser) {

        User user = userRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("회원 찾기 실패"));
        user.setPassword(reqUser.getPassword());
        user.setEmail(reqUser.getEmail());

        return user;
    }

    @Transactional
    public void update(User user, PrincipalDetail principal) {

        User persistence = userRepo.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("회원 찾기 실패"));

        if(persistence.getOauth() == null || persistence.getOauth().equals("")) {
            String encPwd = encoder.encode(user.getPassword());
            persistence.setPassword(encPwd);
            persistence.setEmail(user.getEmail());
            principal.setUser(persistence);
        }

    }

    @Transactional(readOnly = true)
    public User findUser(String username) {
        return userRepo.findByUsername(username).orElseGet(() -> new User());
    }

//    @Transactional(readOnly = true) // SELECT 시 트랜잭션 - 정합성 유지
//    public User login(User user) {
//        return userRepo.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//    }
}
