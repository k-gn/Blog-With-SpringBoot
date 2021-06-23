package com.cos.blog.service;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// service 가 필요한 이유
// 1. 트랜잭션 관리 (트랜잭션 : 일이 처리되기 위한 가장 작은 단위)
// 2. 하나의 기능으로 처리 (서비스화, 여러 기능이 하나로 모인게 하나의 기능으로 처리될 수도 있다.)
@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Transactional // 정합성 유지를 위해서도 사용한다. (같은 트랜잭션 내에서는 동일한 데이터를 볼 수 있도록 보장된다. - REPEACTABLE READ)
    public void save(User user) {
        userRepo.save(user);
    }

    @Transactional
    public User update(Long id, User reqUser) {

        User user = userRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("수정 실패"));
        user.setPassword(reqUser.getPassword());
        user.setEmail(reqUser.getEmail());

        return user;
    }
}
