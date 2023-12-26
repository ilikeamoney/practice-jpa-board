package com.example.jpa_board.repository;

import com.example.jpa_board.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("SELECT b FROM Board b WHERE b.member.id = :memId")
    List<Board> getMemberBoard(@Param("memId") Long memId);

    void deleteBoardByMemberId(Long memId);
}
