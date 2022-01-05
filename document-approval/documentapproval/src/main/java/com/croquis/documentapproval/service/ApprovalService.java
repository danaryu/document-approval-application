package com.croquis.documentapproval.service;

import com.croquis.documentapproval.domain.Document;
import com.croquis.documentapproval.domain.DocumentApproval;
import com.croquis.documentapproval.domain.DocumentStatus;
import com.croquis.documentapproval.domain.Member;
import com.croquis.documentapproval.exception.ErrorCode;
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

    /**
     * INBOX 조회
     * - 결재 상태 진행 중
     * - 결재자가 Login한 Member 본인인 경우
     */
    public List<DocumentApproval> findInbox(String email) {
        Member foundMember = getMember(memberRepository, email);
        return documentApprovalRepository.findAllByApproverAndDocumentApprovalStatus(foundMember, DocumentStatus.PROCESSING);
    }

    public void approveDocument(Long approvalId, String comment) {
        DocumentApproval foundDocumentApproval = documentApprovalRepository.findById(approvalId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.INVALID_REQUEST));

        foundDocumentApproval.updateApprovalStatus(DocumentStatus.APPROVED);
        foundDocumentApproval.setComment(comment);

        // 결재 문서에 다음 순서가 있는지 확인
        Document document = foundDocumentApproval.getDocument();
        int nextApprovalSequence = foundDocumentApproval.getApprovalSequence() + 1;
        Optional<DocumentApproval> nextApprovalDocument = documentApprovalRepository.findByDocumentAndApprovalSequence(document, nextApprovalSequence);

        if (!hasNextApprover(nextApprovalDocument)) {
            Document foundDocument = documentRepository.findById(document.getId())
                    .orElseThrow(() -> new NotFoundException(ErrorCode.INVALID_REQUEST));
            document.updateDocumentStatus(DocumentStatus.APPROVED);
            return;
        }

        DocumentApproval documentApproval = nextApprovalDocument
                .orElseThrow(() -> new NotFoundException(ErrorCode.INVALID_REQUEST));
        documentApproval.updateApprovalStatus(DocumentStatus.PROCESSING);
    }

    public void returnDocument(Long approvalId, String comment) {
        DocumentApproval foundDocumentApproval = documentApprovalRepository.findById(approvalId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.INVALID_REQUEST));
        foundDocumentApproval.updateApprovalStatus(DocumentStatus.RETURNED);
        foundDocumentApproval.setComment(comment);

        // TODO 반려된 Document에 대한 Document Approval의 상태도 모두 변경 필요
        foundDocumentApproval.getDocument().updateDocumentStatus(DocumentStatus.RETURNED);
    }

    private boolean hasNextApprover(Optional<DocumentApproval> nextApprovalDocument) {
        return nextApprovalDocument.isPresent();
    }

    /**
     * OUTBOX 조회
     * - 작성자가 Login한 Member 본인인 경우
     * - 결재 진행 중인 문서
     */
    public List<Document> findOutbox(String email) {
        Member foundMember = getMember(memberRepository, email);
        return documentRepository.findAllByAuthorAndDocumentStatus(foundMember, DocumentStatus.PROCESSING);
    }

    /**
     * ARCHIVE 조회
     * - 결재자에 Member 본인이 포함된 경우
     * - 결재가 완료된 문서 (승인/거절)
     */
    public List<Document> findArchive(String email) {
        Member foundMember = getMember(memberRepository, email);
        return documentRepository.findAllDocumentApprovals(foundMember.getId(), DocumentStatus.PROCESSING);
    }

    private Member getMember(MemberRepository memberRepository, String email) {
        Member foundMember = memberRepository.findByEmail(email)
                .orElseThrow(() ->  new NotFoundException(ErrorCode.INVALID_REQUEST));
        return foundMember;
    }

}
