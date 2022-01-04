package com.croquis.documentapproval.service;

import com.croquis.documentapproval.domain.Document;
import com.croquis.documentapproval.domain.DocumentApproval;
import com.croquis.documentapproval.domain.DocumentStatus;
import com.croquis.documentapproval.domain.Member;
import com.croquis.documentapproval.exception.NotFoundException;
import com.croquis.documentapproval.repository.DocumentApprovalRepository;
import com.croquis.documentapproval.repository.DocumentRepository;
import com.croquis.documentapproval.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ApprovalService {

    private final MemberRepository memberRepository;
    private final DocumentRepository documentRepository;
    private final DocumentApprovalRepository documentApprovalRepository;

    public List<DocumentApproval> findInbox(String email) {
        // username으로 member찾기
        Member foundMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("회원 정보를 찾을 수 없습니다. : " + email));

        // Document approval member_id: 나 && status: Processing
        return documentApprovalRepository.findAllByApproverAndDocumentApprovalStatus(foundMember, DocumentStatus.PROCESSING);
    }

    public void approveDocument(Long approvalId, String comment) {
        DocumentApproval foundDocumentApproval = documentApprovalRepository.findById(approvalId).orElseThrow(() -> new NotFoundException("문서 정보를 찾을 수 없습니다." + approvalId));

        // 결재 승인
        foundDocumentApproval.updateApprovalStatus(DocumentStatus.APPROVED);
        // 결재 의견
        foundDocumentApproval.setComment(comment);

        // 다음 결재 문서 체크
        Document document = foundDocumentApproval.getDocument();
        int nextApprovalSequence = foundDocumentApproval.getApprovalSequence() + 1;
        Optional<DocumentApproval> nextApprovalDocument = documentApprovalRepository.findByDocumentAndApprovalSequence(document, nextApprovalSequence);

        if(!hasNextApprover(nextApprovalDocument)) {
            System.out.println("document.getId() = " + document.getId());
            Document foundDocument = documentRepository.findById(document.getId())
                    .orElseThrow(() -> new NotFoundException("문서 정보를 찾을 수 없습니다."));
            document.updateDocumentStatus(DocumentStatus.APPROVED);
            return;
        }

        DocumentApproval documentApproval = nextApprovalDocument
                .orElseThrow(() -> new NotFoundException("결재 문서 정보를 찾을 수 없습니다. " + nextApprovalSequence));
        documentApproval.updateApprovalStatus(DocumentStatus.PROCESSING);
    }

    public void returnDocument(Long approvalId, String comment) {
        DocumentApproval foundDocumentApproval = documentApprovalRepository.findById(approvalId).orElseThrow(() -> new NotFoundException("문서 정보를 찾을 수 없습니다." + approvalId));

        // 결재 승인
        foundDocumentApproval.updateApprovalStatus(DocumentStatus.RETURNED);
        // 결재 의견
        foundDocumentApproval.setComment(comment);

        // TODO 반려된 Document에 대한 Document Approval의 상태도 모두 변경
        // 문서 상태 변경
        foundDocumentApproval.getDocument().updateDocumentStatus(DocumentStatus.RETURNED);
    }

    private boolean hasNextApprover(Optional<DocumentApproval> nextApprovalDocument) {
        return nextApprovalDocument.isPresent();
    }

}
