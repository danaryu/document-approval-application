package com.croquis.documentapproval;

import com.croquis.documentapproval.domain.*;
import com.croquis.documentapproval.repository.ClassificationRepository;
import com.croquis.documentapproval.repository.DocumentApprovalRepository;
import com.croquis.documentapproval.repository.DocumentRepository;
import com.croquis.documentapproval.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;


@Transactional
@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {

    private final MemberRepository memberRepository;
    private final DocumentRepository documentRepository;
    private final DocumentApprovalRepository documentApprovalRepository;
    private final ClassificationRepository classificationRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Member memberA = Member.builder()
                .id(1L)
                .email("MemberA@hello.com")
                .password(passwordEncoder.encode("hello"))
                .username("MemberA")
                .authority("ROLE_MEMBER")
                .build();

        Member memberB = Member.builder()
                .id(2L)
                .email("MemberB@hello.com")
                .password(passwordEncoder.encode("hello"))
                .username("MemberB")
                .authority("ROLE_MEMBER")
                .build();

        Member memberC = Member.builder()
                .id(3L)
                .email("MemberC@hello.com")
                .password(passwordEncoder.encode("hello"))
                .username("MemberC")
                .authority("ROLE_MEMBER")
                .build();

        Member memberD = Member.builder()
                .id(4L)
                .email("MemberD@hello.com")
                .password(passwordEncoder.encode("hello"))
                .username("MemberD")
                .authority("ROLE_MEMBER")
                .build();

        Member memberE = Member.builder()
                .id(5L)
                .email("MemberE@hello.com")
                .password(passwordEncoder.encode("hello"))
                .username("MemberE")
                .authority("ROLE_MEMBER")
                .build();

        Member memberF = Member.builder()
                .id(6L)
                .email("MemberF@gmail.com")
                .password(passwordEncoder.encode("hello"))
                .username("MemberF")
                .authority("ROLE_MEMBER")
                .build();

        memberRepository.save(memberA);
        memberRepository.save(memberB);
        memberRepository.save(memberC);
        memberRepository.save(memberD);
        memberRepository.save(memberE);
        memberRepository.save(memberF);

        Classification classificationA = new Classification(1L,"VACATION", null);
        List<Classification> classifications = Arrays.asList(
                classificationA,
                new Classification(2L,"holiday", classificationA),
                new Classification(3L, "WORKSHOP", null),
                new Classification(4L, "SICKDAY", null),
                new Classification(5L, "BUSINESS TRIP", null)
        );
        classificationRepository.saveAll(classifications);

        Document document = Document.builder()
                .id(1L)
                .title("결재 상신드립니다.")
                .content("휴가쓰겠습니다.")
                .author(memberA)
                .documentStatus(DocumentStatus.PROCESSING)
                .classification(classificationA)
                .build();
        documentRepository.save(document);

        DocumentApproval documentApproval = new DocumentApproval(1L, memberA, document, 1, DocumentStatus.PROCESSING);
        List<DocumentApproval> documentApprovals = Arrays.asList(
                documentApproval,
                new DocumentApproval(2L, memberB, document, 2, null),
                new DocumentApproval(3L, memberC, document, 3, null),
                new DocumentApproval(4L, memberD, document, 4, null),
                new DocumentApproval(5L, memberE, document, 5, null)
        );
        documentApprovalRepository.saveAll(documentApprovals);
    }

};
