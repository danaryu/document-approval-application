package com.croquis.documentapproval.repository;

import com.croquis.documentapproval.domain.Document;
import com.croquis.documentapproval.domain.DocumentApproval;
import com.croquis.documentapproval.domain.DocumentStatus;
import com.croquis.documentapproval.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findAllByAuthorAndDocumentStatus(Member author, DocumentStatus documentStatus);

    @Query("select d " +
            "from DocumentApproval as da, Document as d, Member as m " +
            "where da.document.id = d.id " +
            "and da.approver.id = m.id " +
            "and da.approver.id = :id " +
            "and d.documentStatus != :documentStatus")
    List<Document> findAllDocumentApprovals(@Param("id") Long id, @Param("documentStatus") DocumentStatus documentStatus);
}
