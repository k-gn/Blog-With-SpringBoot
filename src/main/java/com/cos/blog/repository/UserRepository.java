package com.cos.blog.repository;

import com.cos.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    // JPA Naming 쿼리
    User findByUsernameAndPassword(String username, String password);

//    @Query(value = "SELECT * FROM USER WHERE username = :username AND password = :password", nativeQuery = true)
//    User login(String username, String password);
}
