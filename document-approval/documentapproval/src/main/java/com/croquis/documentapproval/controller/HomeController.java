package com.croquis.documentapproval.controller;

import com.croquis.documentapproval.domain.Member;
import com.croquis.documentapproval.exception.ErrorCode;
import com.croquis.documentapproval.exception.NotFoundException;
import com.croquis.documentapproval.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;

    @GetMapping("")
    public String main() { return "index"; }

    @GetMapping("login")
    public String login() { return "login/login"; }

    @GetMapping("login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "/login/login";
    }

    @GetMapping("home")
    public String homeMenu(@AuthenticationPrincipal User user, Model model) {
        String loginUser = user.getUsername();
        Member foundMember = memberRepository.findByEmail(loginUser)
                .orElseThrow(() ->  new NotFoundException(ErrorCode.INVALID_REQUEST));
        model.addAttribute("member", foundMember);
        return "home";
    }


}
