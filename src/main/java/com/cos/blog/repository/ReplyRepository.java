package com.cos.blog.repository;

import com.cos.blog.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Modifying // 조회가 아닌 삽입 삭제 수정 시 필요 (특히 수정, 삭제 쿼리에선 필수)
    @Query(value = "INSERT INTO reply (content, userId, boardId, createDate) VALUES(:content, :userId, :boardId, now())", nativeQuery = true)
    int mSave(String content, Long userId, Long boardId); // int 만 리턴 가능 (업데이트된 행의 개수)

}
