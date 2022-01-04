package com.croquis.documentapproval.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
@Transactional
@Rollback(value = false)
class DocumentTest {

    @PersistenceContext
    EntityManager em;

    private Member memberA;
    private Document documentA;

    @BeforeEach
    public void set() throws Exception {
        memberA = Member.builder()
                .email("danadot@gmail.com")
                .password("hello")
                .username("danadot123")
                .build();

        documentA = Document.builder()
                .title("helloworld")
                .content("helloworld")
                .documentStatus(DocumentStatus.PROCESSING)
                .build();
    }

    @Test
    @DisplayName("문서의 작성자를 설정할 수 있다.")
    public void writtenBy() throws Exception {
        documentA.writtenBy(memberA);
        em.persist(memberA);
        em.persist(documentA);
    }

    @Test
    @DisplayName("문서의 분류를 설정할 수 있다.")
    public void classification() throws Exception {
        Classification classification = new Classification("hello", null);
        Classification classificationChild = new Classification("hello child", classification);

        documentA.setClassification(classificationChild);

        em.persist(classification);
        em.persist(classificationChild);
    }

}