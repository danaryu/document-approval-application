package com.croquis.documentapproval.repository;

import com.croquis.documentapproval.domain.Document;
import com.croquis.documentapproval.domain.DocumentApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
//    List<Document> findAllByDocumentApprovals(List<DocumentApproval> documentApprovals);
}
