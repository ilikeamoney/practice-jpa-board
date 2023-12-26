package com.example.jpa_board.domain.entity;

import com.example.jpa_board.domain.edit.BoardEdit;
import com.example.jpa_board.domain.form.BoardForm;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {
    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mem_id")
    private Member member;

    @Builder
    public Board(BoardForm boardForm, Member member) {
        this.title = boardForm.getTitle();
        this.content = boardForm.getContent();
        this.member = member;
        member.boards.add(this);
    }

    public void edit(BoardEdit boardEdit) {
        this.title = boardEdit.getTitle() != null ?  boardEdit.getTitle() : this.getTitle();
        this.content = boardEdit.getContent() != null ?  boardEdit.getContent() : this.getContent();
    }
}
