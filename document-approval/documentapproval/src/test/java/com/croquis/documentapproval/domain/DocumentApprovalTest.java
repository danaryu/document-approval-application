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
class DocumentApprovalTest {

    @PersistenceContext
    EntityManager em;

    private Member memberA;
    private Document documentA;
    private DocumentApproval documentApproval = new DocumentApproval();

    @BeforeEach
    public void document_approval_set() throws Exception {
        memberA = Member.builder()
                .email("helloworld@gmail.com")
                .password("hello")
                .username("danadotA")
                .build();

        documentA = Document.builder()
                .title("helloworld")
                .content("helloworld")
                .documentStatus(DocumentStatus.PROCESSING)
                .build();

        documentA.writtenBy(memberA);

        em.persist(memberA);
        em.persist(documentA);
    }

    @Test
    @DisplayName("결재문서의 결재 상태를 변경할 수 있다.")
    public void updateApprovalStatus() throws Exception {
        DocumentApproval documentApproval = new DocumentApproval(memberA, documentA, 1L);
        documentApproval.updateApprovalStatus(DocumentStatus.PROCESSING);

        em.persist(documentApproval);
    }

    @Test
    @DisplayName("결재문서에 결재자를 추가할 수 있다.")
    public void addApprover() throws Exception {
        documentApproval.addApprover(memberA);
    }

    @Test
    @DisplayName("결재문서에 문서를 세팅할 수 있다.")
    public void setDocument() throws Exception {
        documentApproval.setDocument(documentA);
    }


}