package com.croquis.documentapproval.domain;

import com.croquis.documentapproval.common.BaseTime;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "document_approval")
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

    public DocumentApproval(Member approver, Document document) {
        this.approver = approver;
        this.document = document;
    }

}
