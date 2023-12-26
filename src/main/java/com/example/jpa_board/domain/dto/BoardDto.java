package com.example.jpa_board.domain.dto;

import lombok.Data;

@Data
public class BoardDto {
    private Long id;
    private Long memId;
    private String title;
    private String content;
    private String writerId;
}
