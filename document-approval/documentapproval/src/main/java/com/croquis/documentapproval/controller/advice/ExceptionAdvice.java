package com.croquis.documentapproval.controller.advice;

import com.croquis.documentapproval.domain.Member;
import com.croquis.documentapproval.exception.ErrorCode;
import com.croquis.documentapproval.exception.NotFoundException;
import com.croquis.documentapproval.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {

    private final MemberRepository memberRepository;

    // TODO 공통 Exception 처리
    @ExceptionHandler(NotFoundException.class)
    public String notFoundExceptionHandler(NotFoundException e, Model model) {
        log.error("###### NotFoundException ###### ");
        return "home";
    }

    @ExceptionHandler(Exception.class)
    public String exceptionHandler(@AuthenticationPrincipal User user, Exception e, Model model) {
        log.error("###### Exception ###### ");
        String loginUser = user.getUsername();
        Member foundMember = memberRepository.findByEmail(loginUser)
                .orElseThrow(() ->  new NotFoundException(ErrorCode.INVALID_REQUEST));
        model.addAttribute("member", foundMember);
        return "home";
    }


}
