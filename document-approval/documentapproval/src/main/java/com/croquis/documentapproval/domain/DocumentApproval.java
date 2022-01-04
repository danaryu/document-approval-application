package com.croquis.documentapproval.domain;

import com.croquis.documentapproval.common.BaseTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    private int approvalSequence;

    @Column(name = "document_status")
    @Enumerated(EnumType.STRING)
    private DocumentStatus documentApprovalStatus;

    @Lob
    private String comment;

    public DocumentApproval(Document document, Member approver) {
        this.document = document;
        this.approver = approver;
    }

    private DocumentApproval(Member approver, Document document, int approvalSequence, DocumentStatus documentApprovalStatus) {
        this.approver = approver;
        this.document = document;
        this.approvalSequence = approvalSequence;
        this.documentApprovalStatus = documentApprovalStatus;
    }

    public DocumentApproval(long id, Member approver, Document document, int approvalSequence, DocumentStatus documentApprovalStatus) {
        this.id = id;
        this.approver = approver;
        this.document = document;
        this.approvalSequence = approvalSequence;
        this.documentApprovalStatus = documentApprovalStatus;
    }

    public static DocumentApproval createDocumentApproval(Member approver, Document document, int approvalSequence, DocumentStatus documentApprovalStatus) {
        return new DocumentApproval(approver, document, approvalSequence, documentApprovalStatus);
    }

    public void updateApprovalStatus(DocumentStatus status) {
        this.documentApprovalStatus = status;
    }

    public void addApprover(Member approver) {
        this.approver = approver;
    }

    public void setDocument(Document document) {
        this.document = document;
        document.getDocumentApprovals().add(this);
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
