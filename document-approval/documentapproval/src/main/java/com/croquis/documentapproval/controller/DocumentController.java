package com.croquis.documentapproval.controller;

import com.croquis.documentapproval.domain.*;
import com.croquis.documentapproval.dto.DocumentForm;
import com.croquis.documentapproval.exception.ErrorCode;
import com.croquis.documentapproval.exception.NotFoundException;
import com.croquis.documentapproval.repository.ClassificationRepository;
import com.croquis.documentapproval.repository.MemberRepository;
import com.croquis.documentapproval.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;
    private final MemberRepository memberRepository;
    private final ClassificationRepository classificationRepository;

    @GetMapping("/new/{memberId}")
    public String createDocumentForm(Model model, @PathVariable Long memberId) {
        Member foundMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new UsernameNotFoundException("회원 정보를 찾을 수 없습니다. memberId: " + memberId));
        List<Classification> mainClassification = classificationRepository.findByParentIsNull();
        List<Member> memberList = memberRepository.findAll();

        model.addAttribute("form", new DocumentForm());
        model.addAttribute("author", foundMember);
        model.addAttribute("classifications", mainClassification);
        model.addAttribute("approverList", memberList);

        return "documents/createDocumentForm";
    }

    @PostMapping("/new")
    public String createDocument(@ModelAttribute("form") DocumentForm form) {
        Member firstApprover = memberRepository.findById(form.getFirstApproverId()).orElseThrow(() -> new NotFoundException(ErrorCode.INVALID_REQUEST));

        List<Member> otherApprovers = new ArrayList<>();
        otherApprovers.add(memberRepository.findById(form.getSecondApproverId()).orElseThrow(() -> new NotFoundException(ErrorCode.INVALID_REQUEST)));
        otherApprovers.add(memberRepository.findById(form.getThirdApproverId()).orElseThrow(() -> new NotFoundException(ErrorCode.INVALID_REQUEST)));
        otherApprovers.add(memberRepository.findById(form.getFourthApproverId()).orElseThrow(() -> new NotFoundException(ErrorCode.INVALID_REQUEST)));
        otherApprovers.add(memberRepository.findById(form.getFifthApproverId()).orElseThrow(() -> new NotFoundException(ErrorCode.INVALID_REQUEST)));
        documentService.saveDocument(form.getAuthorName(), form.getTitle(), form.getContent(), form.getClassificationId(), firstApprover, otherApprovers);

        return "redirect:/home";
    }

    @GetMapping("/{documentId}")
    public String findDocumentDetail(Model model, @PathVariable("documentId") Long documentId) {
        Document documentDetail = documentService.findDocumentDetail(documentId);
        model.addAttribute("documentDetail", documentDetail);
        return "documents/documentDetail";
    }

}
