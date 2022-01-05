package com.croquis.documentapproval.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public enum DocumentStatus {

    // 결재 진행중
    PROCESSING("PROCESSING", "결재 진행 중"),
    // 결재 승인
    APPROVED("APPROVED", "결재 승인"),
    // 결재 반려
    RETURNED("RETURNED", "결재 반려");

    private String status;
    private String value;

}
