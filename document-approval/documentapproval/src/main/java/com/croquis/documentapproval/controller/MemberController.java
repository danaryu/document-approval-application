package com.croquis.documentapproval.controller;

import com.croquis.documentapproval.auth.JwtTokenProvider;
import com.croquis.documentapproval.domain.Authority;
import com.croquis.documentapproval.dto.GeneratedToken;
import com.croquis.documentapproval.dto.MemberLoginRequest;
import com.croquis.documentapproval.dto.MemberLoginResponse;
import com.croquis.documentapproval.exception.UnauthorizedException;
import com.croquis.documentapproval.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class MemberController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;

    @PostMapping("login")
    public ResponseEntity<GeneratedToken> login(@RequestBody MemberLoginRequest memberLoginRequest) {
        System.out.println("memberLoginRequest = " + memberLoginRequest.toString());
        return ResponseEntity.ok(memberService.login(memberLoginRequest));
    }
}
