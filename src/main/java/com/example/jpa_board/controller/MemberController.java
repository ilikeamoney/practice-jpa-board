package com.example.jpa_board.controller;

import com.example.jpa_board.domain.form.EditPassword;
import com.example.jpa_board.domain.form.LoginForm;
import com.example.jpa_board.domain.form.MemberForm;
import com.example.jpa_board.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/member_join")
    public String memberJoinGet() {
        return "view/member/member_join_form";
    }

    @PostMapping("/member_join")
    public String memberJoinPost(@ModelAttribute MemberForm memberForm) {
        memberService.join(memberForm);

        return "redirect:/";
    }

    @GetMapping("/member_login")
    public String memberLoginGet() {
        return "view/member/member_login_form";
    }


    @PostMapping("/member_login")
    public String memberLoginPost(@ModelAttribute LoginForm loginForm, HttpServletRequest req) {
        Long login = memberService.login(loginForm);
        req.getSession().setAttribute("memLog", login);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.invalidate();

        return "redirect:/";
    }

    @GetMapping("/my_info")
    public String myInfo(HttpServletRequest req,
                         Model model) {
        HttpSession session = req.getSession();
        Long memId = (Long) session.getAttribute("memLog");
        model.addAttribute("member", memberService.findMember(memId));

        return "view/member/member_info";
    }

    @GetMapping("/edit_valid")
    public String editValid() {
        return "view/member/member_edit_valid";
    }

    @PostMapping("/edit_valid")
    public String editValidPost(@ModelAttribute LoginForm loginForm,
                                HttpServletRequest req) {

        HttpSession session = req.getSession();
        Long loginId = (Long)session.getAttribute("memLog");
        Long currentMemberId = memberService.login(loginForm);

        if (!loginId.equals(currentMemberId)) {
            throw new IllegalStateException("현재 로그인 정보가 다릅니다");
        }

        return "view/member/member_edit_form";
    }

    @PostMapping("/edit_password")
    public String editPassword(@ModelAttribute EditPassword editPassword,
                               HttpServletRequest req) {
        HttpSession session = req.getSession();
        Long memId = (Long) session.getAttribute("memLog");
        memberService.editPassword(memId, editPassword);
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/remove_member")
    public String removeMemberGet() {
        return "view/member/member_remove_form";
    }

    @PostMapping("/remove_member")
    public String removeMemberPost(@ModelAttribute LoginForm loginForm,
                                   HttpServletRequest req) {
        HttpSession session = req.getSession();
        Long memId = (Long) session.getAttribute("memLog");
        Long loginId = memberService.login(loginForm);

        if (!memId.equals(loginId)) {
            throw new IllegalStateException("현재 로그인 정보가 다릅니다");
        }

        memberService.removeMember(loginId);
        session.invalidate();
        return "redirect:/";
    }
}
