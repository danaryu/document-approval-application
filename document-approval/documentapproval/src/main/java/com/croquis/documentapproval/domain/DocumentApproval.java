package com.croquis.documentapproval.domain;

import com.croquis.documentapproval.common.BaseTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.LinkedList;

@Entity
@Getter
@Table(name = "document_approval")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DocumentApproval extends BaseTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_approval_to_approver"))
    private Member approver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_approval_to_document"))
    private Document document;

    @Column(name = "approval_sequence")
    private Long approvalSequence;

    @Column(name = "document_status")
    @Enumerated(EnumType.STRING)
    private DocumentStatus documentApprovalStatus;

    @Lob
    private String comment;

    public DocumentApproval(Member approver, Document document, Long approvalSequence) {
        this.approver = approver;
        this.document = document;
        this.approvalSequence = approvalSequence;
    }

    public void updateApprovalStatus(DocumentStatus status) {
        this.documentApprovalStatus = status;
    }

    public void addApprover(Member approver) {
        this.approver = approver;
    }

    public void setDocument(Document document) {
        this.document = document;
        document.addDocumentApproval(this);
    }


}
