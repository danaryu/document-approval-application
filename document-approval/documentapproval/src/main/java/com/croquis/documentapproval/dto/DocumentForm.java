package com.croquis.documentapproval.dto;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DocumentForm {
    private String title;
    private String content;
    private Long classificationId;
    private String authorName;

    private Long firstApproverId;
    private Long secondApproverId;
    private Long thirdApproverId;
    private Long fourthApproverId;
    private Long fifthApproverId;

}
