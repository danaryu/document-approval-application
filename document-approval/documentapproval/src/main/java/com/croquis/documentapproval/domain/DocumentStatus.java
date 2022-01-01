package com.croquis.documentapproval.domain;

import lombok.Getter;

@Getter
public enum DocumentStatus {

    // 결재 진행중
    PROCESSING("PROCESSING", "결재 진행 중"),
    // 결재 승인
    APPROVED("APPROVED", "결재 승인"),
    // 결재 반려
    RETURNED("RETURNED", "결재 반려");

    private String status;
    private String value;

    DocumentStatus(String status, String value) {
        this.status = status;
        this.value = value;
    }

}
