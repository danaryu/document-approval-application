package com.croquis.documentapproval.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "classification")
public class Classification {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Classification parent;

    @OneToMany(mappedBy = "parent")
    private List<Classification> child = new ArrayList<>();

}
