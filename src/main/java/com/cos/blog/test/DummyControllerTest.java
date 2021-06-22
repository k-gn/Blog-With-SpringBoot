package com.cos.blog.test;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        // 스프링 부트는 MessageConverter 가 응답 시 자동 작동
        // 만약 자바 오브젝트를 리턴하면 MessageConverter 가 Jackson 라이브러리를 호출해서
        // user 오브젝트를 json 으로 변환해서 브라우저에 던진다.
        return user;
    }

    @GetMapping("/dummy/users")
    public List<User> list() {
        return userRepo.findAll();
    }

    // 페이징 처리
    @GetMapping("/dummy/user")
    public List<User> pageList(@PageableDefault(size = 1, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
//        Pageable pageInfo = PageRequest.of(1, 2, Sort.by("id").ascending());
        Page<User> pagingUser = userRepo.findAll(pageable);
        return pagingUser.getContent();
    }

    @Transactional
    @PutMapping("/dummy/user/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User reqUser) {

        User user = userRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("수정 실패"));
        user.setPassword(reqUser.getPassword());
        user.setEmail(reqUser.getEmail());
//        userRepo.save(user); 

        // 더티체킹
        return null;
    }
}
