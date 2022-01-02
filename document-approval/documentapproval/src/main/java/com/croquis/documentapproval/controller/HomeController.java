package com.croquis.documentapproval.controller;

import com.croquis.documentapproval.domain.Member;
import com.croquis.documentapproval.repository.MemberRepository;
import com.croquis.documentapproval.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;

    @GetMapping("/")
    public String main() { return "index"; }

    @GetMapping("/login")
    public String login() { return "login/login"; }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "/login/login";
    }

    @GetMapping("/home")
    public String homeMenu(@AuthenticationPrincipal User user, Model model) {
        String loginUser = user.getUsername();
        String foundMemberName = memberRepository.findByEmail(loginUser)
                .orElseThrow(() -> new UsernameNotFoundException("회원 정보를 찾을 수 없습니다. email: " + loginUser))
                .getUsername();
        model.addAttribute("memberName", foundMemberName);
        return "/document/home";
    }


}
