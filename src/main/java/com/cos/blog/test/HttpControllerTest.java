package com.cos.blog.test;

import org.springframework.web.bind.annotation.*;
// 브라우저 주소창에서 get으로만 요청을 보낼 수 있다.
@RestController
@RequestMapping("/http")
public class HttpControllerTest {

    @GetMapping("/get")
    public String getTest(Member m) {

        return "get - " + m;
    }

    // raw-text = text/plain
    // raw-json = application/json
    @PostMapping("/post")
    public String postTest(@RequestBody Member m) { // x-www-form-urlencoded 방식이 아닌 요청은 @RequestBody를 써서 body 데이터를 받는다.

        return "post - " + m;
    }

    @PutMapping("/put")
    public String putTest() {

        return "put";
    }

    @DeleteMapping("/delete")
    public String deleteTest() {

        return "delete";
    }
}
