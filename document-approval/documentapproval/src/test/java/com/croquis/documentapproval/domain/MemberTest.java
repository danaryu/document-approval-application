package com.croquis.documentapproval.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
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

    private Member memberA, memberB;
    private Document documentA;
    private DocumentApproval documentApproval = new DocumentApproval();

    @BeforeEach
    public void setMember() throws Exception {
        memberA = Member.builder()
                .email("helloworld@gmail.com")
                .password("hello")
                .username("danadotA")
                .build();

        memberB = Member.builder()
                .email("helloworld@gmail.com")
                .password("hello")
                .username("danadotB")
                .build();

        documentA = Document.builder()
                .title("helloworld")
                .content("helloworld")
                .documentStatus(DocumentStatus.PROCESSING)
                .build();

        em.persist(memberA);
        em.persist(memberB);
        em.persist(documentA);
    }

    @Test
    @DisplayName("멤버에 문서를 추가할 수 있다.")
    public void addDocument() throws Exception {
        memberA.addDocument(documentA);
    }

    @Test
    @DisplayName("멤버에 결재문서를 추가할 수 있다.")
    public void addDocumentApproval() throws Exception {
        memberB.addDocumentApproval(documentApproval);
    }

}