package com.croquis.documentapproval.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "document")
public class Document extends BaseTime {

    @Id @GeneratedValue
    @Column(name = "document_id")
    private Long id;

    private String title;
    private String content;

    @OneToOne
    @JoinColumn(name = "classification_id")
    private Classification classification;

    @Enumerated(EnumType.STRING)
    private DocumentStatus documentStatus;

    private String createId;
    private String changeId;

    @OneToMany(mappedBy = "document")
    List<DocumentApproval> documentApprovals = new ArrayList<>();

}
