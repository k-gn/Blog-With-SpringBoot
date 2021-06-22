package com.cos.blog.test;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DummyControllerTest {

    @Autowired
    private UserRepository userRepo;

    @PostMapping("/dummy/join")
    public String join(User user) { // key=value 를 기본으로 파싱(약속, x-www-form-urlencoded)
        user.setRole(RoleType.USER);
        userRepo.save(user);
        return "회원가입이 완료되었습니다.";
    }

    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable Long id) {
//        User user = userRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));
        User user = userRepo.findById(id).orElseThrow(IllegalAccessError::new);
        return user;
    }
}
