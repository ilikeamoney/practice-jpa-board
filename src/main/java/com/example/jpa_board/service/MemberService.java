package com.example.jpa_board.service;

import com.example.jpa_board.domain.edit.BoardEdit;
import com.example.jpa_board.domain.entity.Member;
import com.example.jpa_board.domain.form.EditPassword;
import com.example.jpa_board.domain.form.LoginForm;
import com.example.jpa_board.domain.form.MemberForm;
import com.example.jpa_board.repository.BoardRepository;
import com.example.jpa_board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final BoardRepository boardRepository;

    public void join(MemberForm memberForm) {
        memberRepository.save(Member.builder()
                .memberId(memberForm.getMemberId())
                .memberPw(memberForm.getMemberPw())
                .age(memberForm.getAge())
                .build());
    }

    public Long login(LoginForm loginForm) {
        return memberRepository.login(loginForm.getMemberId(), loginForm.getMemberPw())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));
    }

    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new IllegalStateException("존재하지 않는 멤버입니다"));
    }

    @Transactional
    public void editPassword(Long id, EditPassword editPassword) {
        Member member = findMember(id);
        member.editPassword(editPassword);
    }

    @Transactional
    public void removeMember(Long id) {
        // jpa 를 사용할때 삭제 시점에서
        // 부모와 자식테이블 관계라면
        // 웬만해서는 수동으로 삭제하는 것이 바람직하다
        // 요청에 따라 cascade 를 사용하여 영속성 전이를 통해
        // 자동화 한다.
        boardRepository.deleteBoardByMemberId(id);
        memberRepository.deleteById(id);
    }
}
