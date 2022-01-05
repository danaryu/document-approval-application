package com.croquis.documentapproval.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Table(name ="member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String username;

    @OneToMany(mappedBy = "approver", cascade = CascadeType.ALL)
    List<DocumentApproval>  documentApprovals = new ArrayList<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    List<Document> documents = new ArrayList<>();

    @Column(name = "auth")
    private String auth;

    @Builder
    public Member(Long id, String email, String password, String username, String authority) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.auth = authority;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (String role : auth.split(",")) {
            roles.add(new SimpleGrantedAuthority(role));
        }
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
