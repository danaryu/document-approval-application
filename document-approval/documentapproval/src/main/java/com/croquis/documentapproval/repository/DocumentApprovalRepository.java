package com.croquis.documentapproval.repository;

import com.croquis.documentapproval.domain.Document;
import com.croquis.documentapproval.domain.DocumentApproval;
import com.croquis.documentapproval.domain.DocumentStatus;
import com.croquis.documentapproval.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentApprovalRepository extends JpaRepository<DocumentApproval, Long> {
    List<DocumentApproval> findAllByApprover(Member approver);
    List<DocumentApproval> findAllByApproverAndDocumentApprovalStatus(Member approver, DocumentStatus documentApprovalStatus);
    Optional<DocumentApproval> findByDocumentAndApprovalSequence(Document document, int approvalSequence);
}
