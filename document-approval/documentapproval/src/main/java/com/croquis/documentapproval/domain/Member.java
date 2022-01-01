package com.croquis.documentapproval.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name ="member")
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String username;

    @OneToMany(mappedBy = "approver", cascade = CascadeType.ALL)
    List<DocumentApproval>  documentApprovals = new ArrayList<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    List<Document> documents = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Builder
    public Member(String email, String password, String username, Authority authority) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.authority = authority;
        this.documents = new ArrayList<>();
        this.documentApprovals = new ArrayList<>();
    }

    public void addDocumentApproval(DocumentApproval documentApproval) {
        this.documentApprovals.add(documentApproval);
        documentApproval.addApprover(this);
    }

    public void addDocument(Document document) {
        this.documents.add(document);
    }

}
