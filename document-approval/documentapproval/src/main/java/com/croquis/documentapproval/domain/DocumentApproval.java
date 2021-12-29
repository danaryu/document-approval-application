package com.croquis.documentapproval.domain;

import javax.persistence.*;

@Entity
@Table(name = "document_approval")
public class DocumentApproval extends BaseTime {

    @Id @GeneratedValue
    @Column(name = "document_approval_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "document_approver_name")
    private String documentApproverName;

    @ManyToOne
    @JoinColumn(name = "document_id")
    private Document document;

    @Column(name = "approval_sequence")
    private Long approvalSequence;

    @Column(name = "document_status")
    @Enumerated(EnumType.STRING)
    private DocumentStatus documentApprovalStatus;

    private String comment;

}
