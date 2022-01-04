package com.croquis.documentapproval.repository;

import com.croquis.documentapproval.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Rollback(value = false)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    private Member memberA, memberB;

    @Test
    @DisplayName("Email로 Member를 찾을 수 있다.")
    public void findByEmail() throws Exception {

        memberA = Member.builder()
                .email("helloworld@gmail.com")
                .password(passwordEncoder.encode("helloworld"))
                .username("danadotA")
                .authority("ROLE_MEMBER")
                .build();

        memberB = Member.builder()
                .email("dana40426@gmail.com")
                .password(passwordEncoder.encode("helloworld"))
                .username("dandotB")
                .authority("ROLE_MEMBER")
                .build();

        memberRepository.save(memberA);
        memberRepository.save(memberB);

        assertThat(memberRepository.findByEmail("dana40426@gmail.com").get().getEmail()).isEqualTo(memberB.getEmail());
        assertThat(memberRepository.findByEmail("helloworld@gmail.com").get().getEmail()).isEqualTo(memberA.getEmail());

    }


}