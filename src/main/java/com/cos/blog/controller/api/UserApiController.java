package com.cos.blog.controller.api;

import com.cos.blog.auth.PrincipalDetail;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
//    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth/joinProc")
    public ResponseDto<Integer> save(@RequestBody User user) {
        System.out.println(user);
        userService.save(user);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @PutMapping("/user")
    public ResponseDto<Integer> update(@RequestBody User user, @AuthenticationPrincipal PrincipalDetail principal) {
        System.out.println(user);
        userService.update(user, principal);

//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

//    @PostMapping("/user/login")
//    public ResponseDto<Integer> login(@RequestBody User user, HttpSession session) {
//        System.out.println(user);
//        User principal = userService.login(user);
//        if(principal != null) {
//            session.setAttribute("principal", principal);
//        }
//        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
//    }
}
