package com.example.jpa_board.domain.form;

import lombok.Data;

@Data
public class MemberForm {
    private String memberId;
    private String memberPw;
    private Integer age;
}
