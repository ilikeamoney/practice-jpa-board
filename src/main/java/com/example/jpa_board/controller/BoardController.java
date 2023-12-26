package com.example.jpa_board.controller;

import com.example.jpa_board.domain.dto.BoardDto;
import com.example.jpa_board.domain.edit.BoardEdit;
import com.example.jpa_board.domain.form.BoardForm;
import com.example.jpa_board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/write_board")
    public String writeBoardGet() {
        return "view/board/board_write_form";
    }

    @PostMapping("/write_board")
    public String writeBoardPost(@ModelAttribute BoardForm boardForm,
                                 HttpServletRequest req) {
        HttpSession session = req.getSession();
        Long memId = (Long) session.getAttribute("memLog");

        boardService.writeBoard(boardForm, memId);
        return "redirect:/";
    }

    @GetMapping("/find_all_board")
    public String findAllBoard(Model model) {
        model.addAttribute("boards", boardService.getBoards());
        return "view/board/board_find_all";
    }

    @GetMapping("/board_detail")
    public String boardDetail(@RequestParam("boardId") Long boardId, Model model) {
        model.addAttribute("board", boardService.getBoard(boardId));
        return "view/board/board_detail";
    }

    @GetMapping("/board_update")
    public String updateBoard(@RequestParam Long boardId,
                              HttpServletRequest req,
                              Model model) {
        HttpSession session = req.getSession();
        Long memId = (Long) session.getAttribute("memLog");
        BoardDto board = boardService.getBoard(boardId);

        if (!memId.equals(board.getMemId())) {
            throw new IllegalStateException("작성자와 수정자가 다릅니다.");
        }

        model.addAttribute("board", board);
        return "view/board/board_update";
    }

    @PostMapping("/board_update")
    public String updateBoardPost(@ModelAttribute BoardEdit boardEdit,
                                  @RequestParam Long boardId) {
        boardService.boardUpdate(boardEdit, boardId);
        return "redirect:/board_detail?boardId=" + boardId;
    }

    @GetMapping("/my_boards")
    public String myBoard(HttpServletRequest req,
                          Model model) {
        HttpSession session = req.getSession();
        Long memLog = (Long) session.getAttribute("memLog");
        model.addAttribute("boards", boardService.getMyBoards(memLog));

        return "view/board/board_find_my";
    }

    @GetMapping("/board_delete")
    public String deleteBoard(@RequestParam Long boardId,
                              HttpServletRequest req) {
        HttpSession session = req.getSession();
        Long memLog = (Long) session.getAttribute("memLog");
        BoardDto board = boardService.getBoard(boardId);

        if (!memLog.equals(board.getMemId())) {
            throw new IllegalStateException("로그인한 멤버와 작성한 멤버가 다릅니다.");
        }

        boardService.remove(boardId);
        return "view/board/board_find_all";
    }
}
