package com.croquis.documentapproval.service;

import com.croquis.documentapproval.domain.*;
import com.croquis.documentapproval.exception.ErrorCode;
import com.croquis.documentapproval.exception.NotFoundException;
import com.croquis.documentapproval.repository.ClassificationRepository;
import com.croquis.documentapproval.repository.DocumentApprovalRepository;
import com.croquis.documentapproval.repository.DocumentRepository;
import com.croquis.documentapproval.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.croquis.documentapproval.domain.Document.createDocument;
import static com.croquis.documentapproval.domain.DocumentApproval.*;

@Service
@Transactional
@RequiredArgsConstructor
public class DocumentService {
    private final MemberRepository memberRepository;
    private final DocumentRepository documentRepository;
    private final DocumentApprovalRepository documentApprovalRepository;
    private final ClassificationRepository classificationRepository;

    public void saveDocument(String authorName, String title, String content, Long classificationId, Member firstApprover, List<Member> otherApprovers) {
        Member foundAuthor = memberRepository.findByUsername(authorName)
                .orElseThrow(() -> new NotFoundException(ErrorCode.INVALID_REQUEST));

        Classification foundClassification = classificationRepository.findById(classificationId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.INVALID_REQUEST));

        Document document = createDocument(title, content, DocumentStatus.PROCESSING, foundClassification);
        document.writtenBy(foundAuthor);

        DocumentApproval documentApproval = createDocumentApproval(firstApprover, document, 1, DocumentStatus.PROCESSING);
        // 첫번째 순서 결재자만 결재 진행중으로 insert
        documentApprovalRepository.save(documentApproval);
        // 그외 결재자는 결재순대로 insert,  approval status : null
        otherApprovers.stream()
                .map(approver -> createDocumentApproval(approver, document, otherApprovers.indexOf(approver) + 2, null))
                .forEach(documentApprovalRepository::save);
    }

    public Document findDocumentDetail(Long documentId) {
        return documentRepository.findById(documentId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.INVALID_REQUEST));
    }


}


