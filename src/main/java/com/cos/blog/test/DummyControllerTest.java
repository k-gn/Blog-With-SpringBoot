package com.cos.blog.test;

import com.cos.blog.model.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DummyControllerTest {

    @PostMapping("/dummy/join")
    public String join(User user) { // key=value 를 기본으로 파싱(약속, x-www-form-urlencoded)
        return "회원가입이 완료되었습니다.";
    }
}
