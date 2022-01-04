package com.croquis.documentapproval.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DocumentForm {
    private String title;
    private String content;
    private String classificationId;
    private String authorName;
}
