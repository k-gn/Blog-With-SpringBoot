package com.cos.blog.service;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// service 가 필요한 이유
// 1. 트랜잭션 관리
// 2. 하나의 기능으로 처리 (서비스화, 여러 기능이 하나로 모인게 하나의 기능으로 처리될 수도 있다.)
@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Transactional
    public int save(User user) {
        try {
            userRepo.save(user);
            return 1;
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("회원가입 : " + e.getMessage());
        }
        return -1;
    }

    @Transactional
    public User update(Long id, User reqUser) {

        User user = userRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("수정 실패"));
        user.setPassword(reqUser.getPassword());
        user.setEmail(reqUser.getEmail());

        return user;
    }
}
