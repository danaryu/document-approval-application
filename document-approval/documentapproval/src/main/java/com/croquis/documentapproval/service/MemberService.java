package com.croquis.documentapproval.service;

import com.croquis.documentapproval.domain.Authority;
import com.croquis.documentapproval.domain.Member;
import com.croquis.documentapproval.exception.ErrorCode;
import com.croquis.documentapproval.exception.NotFoundException;
import com.croquis.documentapproval.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() ->  new NotFoundException(ErrorCode.INVALID_REQUEST));

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        if(member.getAuth().equals("ROLE_ADMIN")) grantedAuthorities.add(new SimpleGrantedAuthority(Authority.ADMIN.getValue()));

        grantedAuthorities.add(new SimpleGrantedAuthority(Authority.MEMBER.getValue()));
        return new User(member.getEmail(), member.getPassword(), grantedAuthorities);
    }
}
