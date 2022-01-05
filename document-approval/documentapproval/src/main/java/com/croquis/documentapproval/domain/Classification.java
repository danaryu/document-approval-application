package com.croquis.documentapproval.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "classification")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Classification {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_classification"))
    private Classification parent;

    @OneToMany(mappedBy = "parent")
    private List<Classification> child = new ArrayList<>();

    public Classification(Long id, String name, Classification parent) {
        this.id = id;
        this.name = name;
        this.parent = parent;
    }
}
