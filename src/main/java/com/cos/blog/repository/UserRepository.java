package com.cos.blog.repository;

import com.cos.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    // JPA Naming 쿼리
//    User findByUsernameAndPassword(String username, String password);

    // @Query 사용
//    @Query(value = "SELECT * FROM USER WHERE username = :username AND password = :password", nativeQuery = true)
//    User login(String username, String password);
}
