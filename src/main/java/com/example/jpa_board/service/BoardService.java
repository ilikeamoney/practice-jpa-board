package com.example.jpa_board.service;

import com.example.jpa_board.domain.dto.BoardDto;
import com.example.jpa_board.domain.edit.BoardEdit;
import com.example.jpa_board.domain.entity.Board;
import com.example.jpa_board.domain.entity.Member;
import com.example.jpa_board.domain.form.BoardForm;
import com.example.jpa_board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberService memberService;

    public void writeBoard(BoardForm boardForm, Long memberId) {
        Member member = memberService.findMember(memberId);
        boardRepository.save(
                Board.builder()
                        .boardForm(boardForm)
                        .member(member)
                        .build()
        );
    }

    @Transactional
    public void boardUpdate(BoardEdit boardEdit, Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalStateException("존재하지 않는 게시글"));
        board.edit(boardEdit);
    }

    public List<BoardDto> getMyBoards(Long memId) {
        List<BoardDto> boardDtos = new ArrayList<>();

        for (Board board : boardRepository.getMemberBoard(memId)) {
            BoardDto boardDto = new BoardDto();
            boardDto.setId(board.getId());
            boardDto.setMemId(board.getMember().getId());
            boardDto.setTitle(board.getTitle());
            boardDto.setContent(board.getContent());
            boardDto.setWriterId(board.getMember().getMemberId());

            boardDtos.add(boardDto);
        }

        return boardDtos;
    }

    public List<BoardDto> getBoards() {
        List<BoardDto> boardDtos = new ArrayList<>();

        for (Board board : boardRepository.findAll(Sort.by(Sort.Order.desc("id")))) {
            BoardDto boardDto = new BoardDto();
            boardDto.setId(board.getId());
            boardDto.setMemId(board.getMember().getId());
            boardDto.setTitle(board.getTitle());
            boardDto.setContent(board.getContent());
            boardDto.setWriterId(board.getMember().getMemberId());
            boardDtos.add(boardDto);
        }

        return boardDtos;
    }

    public BoardDto getBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalStateException("존재하지 않는 게시글입니다."));
        BoardDto boardDto = new BoardDto();
        boardDto.setId(board.getId());
        boardDto.setMemId(board.getMember().getId());
        boardDto.setTitle(board.getTitle());
        boardDto.setContent(board.getContent());
        boardDto.setWriterId(board.getMember().getMemberId());

        return boardDto;
    }

    @Transactional
    public void remove(Long boardId) {
        boardRepository.deleteById(boardId);
    }
}
