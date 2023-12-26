package com.example.jpa_board.domain.entity;


import com.example.jpa_board.domain.form.EditPassword;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @Column(name = "mem_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String memberId;
    private String memberPw;
    private Integer age;

    @OneToMany(mappedBy = "member")
    List<Board> boards = new ArrayList<>();

    @Builder
    public Member(String memberId, String memberPw, Integer age) {
        this.memberId = memberId;
        this.memberPw = memberPw;
        this.age = age;
    }

    public void editPassword(EditPassword editPassword) {
        this.memberPw =
                editPassword.getEditPassword() != null && editPassword.getEditPassword().equals(editPassword.getEditPasswordValid())
                ? editPassword.getEditPassword() : this.memberPw;
    }
}
