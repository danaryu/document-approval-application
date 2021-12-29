package com.croquis.documentapproval.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "document_classification")
public class Classification {

    @Id @GeneratedValue
    @Column(name = "classification_id")
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Classification parent;

    @OneToMany(mappedBy = "parent")
    private List<Classification> child = new ArrayList<>();

}
