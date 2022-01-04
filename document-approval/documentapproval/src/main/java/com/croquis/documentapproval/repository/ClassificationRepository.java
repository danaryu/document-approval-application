package com.croquis.documentapproval.repository;

import com.croquis.documentapproval.domain.Classification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassificationRepository extends JpaRepository<Classification, Long> {
    List<Classification> findByParentIsNull();
}
