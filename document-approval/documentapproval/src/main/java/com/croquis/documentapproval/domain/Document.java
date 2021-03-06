package com.croquis.documentapproval.domain;

import com.croquis.documentapproval.common.BaseTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "document")
public class Document extends BaseTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String content;

    @OneToOne
    @JoinColumn(name = "classification_id")
    private Classification classification;

    @Enumerated(EnumType.STRING)
    private DocumentStatus documentStatus;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_document_to_author"), nullable = false)
    private Member author;

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL)
    List<DocumentApproval> documentApprovals = new ArrayList<>();

    @Builder
    public Document(Long id, String title, String content, Classification classification, DocumentStatus documentStatus, Member author, List<DocumentApproval> documentApprovals) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.classification = classification;
        this.documentStatus = documentStatus;
        this.author = author;
        this.documentApprovals = new ArrayList<>();
    }

    public Document writtenBy(Member author) {
        this.author = author;
        author.addDocument(this);
        return this;
    }

    public static Document createDocument(Member author, String title, String content, DocumentStatus processing, Classification foundClassification) {
        Document document = Document.builder()
                .title(title)
                .content(content)
                .documentStatus(DocumentStatus.PROCESSING)
                .classification(foundClassification)
                .build();
        document.writtenBy(author);
        return document;
    }

    public void addDocumentApproval(DocumentApproval documentApproval) {
        documentApprovals.add(documentApproval);
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
    }

    public void updateDocumentStatus(DocumentStatus status) {
        this.documentStatus = status;
    }
}
