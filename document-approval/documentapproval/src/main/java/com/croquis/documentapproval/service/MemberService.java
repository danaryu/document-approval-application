package com.croquis.documentapproval.service;

import com.croquis.documentapproval.auth.JwtTokenProvider;
import com.croquis.documentapproval.domain.RefreshToken;
import com.croquis.documentapproval.dto.GeneratedToken;
import com.croquis.documentapproval.dto.MemberLoginRequest;
import com.croquis.documentapproval.dto.MemberLoginResponse;
import com.croquis.documentapproval.repository.MemberRepository;
import com.croquis.documentapproval.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Provider;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public MemberLoginResponse findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .map(MemberLoginResponse::of)
                .orElseThrow(() -> new UsernameNotFoundException("멤버 정보가 없습니다."));
    }

    @Transactional
    public GeneratedToken login(MemberLoginRequest memberLoginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = memberLoginRequest.toAuthentication();
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        GeneratedToken generatedToken = tokenProvider.generateToken(authentication);

        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(generatedToken.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);
        return generatedToken;
    }

}
