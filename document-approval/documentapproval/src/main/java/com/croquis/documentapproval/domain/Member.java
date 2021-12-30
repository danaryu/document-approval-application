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

    @Builder
    public Member(Long id, String email, String password, String username) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.documents = new ArrayList<>();
        this.documentApprovals = new ArrayList<>();
    }

    public void addDocument(Document document) {
        this.documents.add(document);
    }

}
