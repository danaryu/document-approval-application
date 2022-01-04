package com.croquis.documentapproval.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberTest {

    @PersistenceContext
    EntityManager em;

    private Member memberA, memberB, memberC, memberD, memberE, memberF;
    private Document documentA;
    private DocumentApproval documentApproval = new DocumentApproval();

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setMember() throws Exception {
        memberA = Member.builder()
                .email("helloworld@gmail.com")
                .password(passwordEncoder.encode("hello"))
                .username("danadotA")
                .authority("ROLE_MEMBER")
                .build();

        memberB = Member.builder()
                .email("dana40426@gmail.com")
                .password(passwordEncoder.encode("hello"))
                .username("dandotB")
                .authority("ROLE_MEMBER")
                .build();

        memberC = Member.builder()
                .email("approver1@gmail.com")
                .password(passwordEncoder.encode("hello"))
                .username("dandotC")
                .authority("ROLE_MEMBER")
                .build();

        memberD = Member.builder()
                .email("approver2@gmail.com")
                .password(passwordEncoder.encode("hello"))
                .username("dandotD")
                .authority("ROLE_MEMBER")
                .build();

        memberE = Member.builder()
                .email("approver3@gmail.com")
                .password(passwordEncoder.encode("hello"))
                .username("dandotE")
                .authority("ROLE_MEMBER")
                .build();

        memberF = Member.builder()
                .email("apporver4@gmail.com")
                .password(passwordEncoder.encode("hello"))
                .username("dandotF")
                .authority("ROLE_MEMBER")
                .build();

        em.persist(memberA);
        em.persist(memberB);
        em.persist(memberC);
        em.persist(memberD);
        em.persist(memberE);
        em.persist(memberF);

        documentA = Document.builder()
                .title("helloworld")
                .content("helloworld")
                .documentStatus(DocumentStatus.PROCESSING)
                .author(memberA)
                .build();

        em.persist(documentA);
    }

    @Test
    @DisplayName("멤버에 문서를 추가할 수 있다.")
    public void addDocument() throws Exception {
        memberA.addDocument(documentA);
    }
/*

    @Test
    @DisplayName("멤버에 결재문서를 추가할 수 있다.")
    public void addDocumentApproval() throws Exception {
        memberB.addDocumentApproval(documentApproval);
    }
*/

}