package com.example.jpa_board.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class MainController {

    @GetMapping("/")
    public String index(HttpServletRequest req, Model model) {
        HttpSession session = req.getSession();
        Long memLog = (Long) session.getAttribute("memLog");

        if (memLog != null) {
            model.addAttribute("memLog", memLog);
        }

        return "index";
    }
}
