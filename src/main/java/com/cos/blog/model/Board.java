package com.cos.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob // 대용량 데이터
    private String content;

    private int count; // 조회수

    @ManyToOne(fetch = FetchType.EAGER) // N:1 연관관계
    @JoinColumn(name = "userId") // 실제 테이블에선 FK가 생성된다.
    private User user; // DB는 오브젝트를 저장할 수 없다, 자바는 오브젝트를 저장할 수 있다.

    // mappedBy : 연관관계의 주인이 아니다. (DB에 컬럼을 만들지 않는다.), Reply의 board를 FK로 쓸것임을 알림
    // board 를 select 할 때 같이 가져오기 위해 필요한 것임을 알림
    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("board") // 데이터 리턴 시 jackson 라이브러리가 동작하는데 그 때 발생하는 무한참조 방지
    @OrderBy("id desc") // 정렬
    private List<Reply> replys;

    @CreationTimestamp
    private Timestamp createDate;
}

// orphanRemoval : 부모 엔티티와 연관관계가 끊어진 자식 엔티티를 자동으로 삭제
// CascadeType.PERSIST : 엔티티를 영속화 할 때이 필드에 보유 된 엔티티도 유지합니다. EntityManager가 flush 중에 새로운 엔티티를 참조하는 필드를 찾고이 필드가 CascadeType.PERSIST를 사용하지 않으면 오류이므로이 Cascade 규칙의 자유로운 적용을 제안합니다.
// CascadeType.MERGE : 엔티티 상태를 병합 할 때, 이 필드에 보유 된 엔티티도 병합하십시오.
// CascadeType.REFRESH : 엔티티를 새로 고칠 때, 이 필드에 보유 된 엔티티도 새로 고칩니다.
// CascadeType.REMOVE : 엔티티를 삭제할 때, 이 필드에 보유 된 엔티티도 삭제하십시오.
// CascadeType.DETACH : 부모 엔티티가 detach()를 수행하게 되면, 연관된 엔티티도 detach() 상태가 되어 변경사항이 반영되지 않는다.
// CascadeType.ALL : 모든 Cascade 적용