package com.cos.blog.service;

import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    @Transactional
    public void write(Board board, User user) {
        board.setCount(0);
        board.setUser(user);
        boardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public Page<Board> selectAll(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Board selectOne(Long id) {
        return boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 글을 찾을 수 없습니다."));
    }

    @Transactional
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public void update(Long id, Board reqBoard) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 글을 찾을 수 없습니다."));
        board.setTitle(reqBoard.getTitle());
        board.setContent(reqBoard.getContent());
    }

    @Transactional
    public void replyWrite(Reply reply, Long boardId, User user) {

        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("해당 글을 찾을 수 없습니다."));

        reply.setUser(user);
        reply.setBoard(board);

        replyRepository.save(reply);
    }
}
