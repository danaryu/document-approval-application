package com.croquis.documentapproval.domain;

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
class ClassificationTest {

    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("Classification 자기 참조를 테스트한다.")
    public void set() throws Exception {
        Classification classification = new Classification("hello", null);
        Classification classificationChild = new Classification("hello child", classification);

        em.persist(classification);
        em.persist(classificationChild);
    }

}