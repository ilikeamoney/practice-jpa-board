package com.example.jpa_board.repository;

import com.example.jpa_board.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("SELECT m.id FROM Member m WHERE m.memberId = :memId AND m.memberPw = :memPw")
    Optional<Long> login(@Param("memId") String memId, @Param("memPw") String memPw);
}
