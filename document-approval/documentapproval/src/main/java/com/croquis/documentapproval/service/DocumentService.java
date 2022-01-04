package com.croquis.documentapproval.service;

import com.croquis.documentapproval.domain.*;
import com.croquis.documentapproval.exception.NotFoundException;
import com.croquis.documentapproval.repository.ClassificationRepository;
import com.croquis.documentapproval.repository.DocumentApprovalRepository;
import com.croquis.documentapproval.repository.DocumentRepository;
import com.croquis.documentapproval.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DocumentService {

    private final MemberRepository memberRepository;
    private final DocumentRepository documentRepository;
    private final DocumentApprovalRepository documentApprovalRepository;
    private final ClassificationRepository classificationRepository;

    public void saveDocument(String authorName, String title, String content, Long classificationId, Member firstApprover, List<Member> otherApprovers) {

        // 문서 작성자
        Member foundAuthor = memberRepository.findByUsername(authorName)
                .orElseThrow(() -> new UsernameNotFoundException("회원 정보를 찾을 수 없습니다. : " + authorName));

        Classification foundClassification = classificationRepository.findById(classificationId)
                .orElseThrow(() -> new NotFoundException("분류 정보를 찾을 수 없습니다."));

        // 문서 생성
        Document document = Document.builder()
                .title(title)
                .content(content)
                .documentStatus(DocumentStatus.PROCESSING)
                .classification(foundClassification)
                .build();

        document.writtenBy(foundAuthor);

        // 첫번째 순서 결재자만 결재 진행중으로 insert
        documentApprovalRepository.save(new DocumentApproval(firstApprover, document, 1, DocumentStatus.PROCESSING));

        // 그외 결재자는 결재순대로 insert,  approval status : null
        otherApprovers.stream()
                .map(approver -> new DocumentApproval(approver, document,otherApprovers.indexOf(approver) + 2))
                .forEach(documentApprovalRepository::save);
    }

    public Document findDocumentDetail(Long documentId) {
        return documentRepository.findById(documentId).orElseThrow(() -> new NotFoundException("문서 정보를 찾을 수 없습니다. documentId: " + documentId));
    }
}
