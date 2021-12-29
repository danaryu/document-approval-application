package com.croquis.documentapproval.domain;

import javax.persistence.*;

@Entity
@Table(name ="member")
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String email;
    private String password;
    private String username;

}
