package com.cos.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

// ORM -> JAVA(다른언어) Object -> 테이블로 매핑하는 기술
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
// @DynamicInsert // null 인 데이터는 insert 에서 제외
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100, unique = true)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 50)
    private String email;

    // 보통 권한은 권한객체 또는 Enum으로 선언한다.
    // @ColumnDefault("'user'") // 기본값 ('' 써서 문자임을 알려줘야한다.)
    // DB 는 RoleType 이라는게 없다.
    // @Enumerated : 해당 Enum을 엔티티에서 사용할 수 있다.
    // EnumType.ORDINAL : enum 순서 값을 DB에 저장
    // EnumType.STRING : enum 이름을 DB에 저장
    @Enumerated(EnumType.STRING)
    private RoleType role;

    private String oauth;

    @CreationTimestamp // 현재시간 자동 입력
    private Timestamp createDate;

}
