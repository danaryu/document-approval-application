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
        Classification classificationA = new Classification(1L, "VACATION", null);
        Classification classificationB = new Classification(2L,"WORKSHOP", null);
        Classification classificationC = new Classification(3L,"VACATION01", null);
        Classification classificationD = new Classification(4L,"VACATION02", null);
        Classification classificationChild = new Classification(5L,"hello child", classificationA);

        em.persist(classificationA);
        em.persist(classificationB);
        em.persist(classificationC);
        em.persist(classificationD);
        em.persist(classificationChild);
    }

}