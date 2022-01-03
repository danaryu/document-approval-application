package com.croquis.documentapproval.controller;

import com.croquis.documentapproval.domain.Classification;
import com.croquis.documentapproval.domain.Member;
import com.croquis.documentapproval.dto.DocumentForm;
import com.croquis.documentapproval.repository.ClassificationRepository;
import com.croquis.documentapproval.repository.MemberRepository;
import com.croquis.documentapproval.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("form", new DocumentForm());
        model.addAttribute("author", foundMember);
        model.addAttribute("classifications", mainClassification);
        return "documents/createDocumentForm";
    }

    @PostMapping("/new")
    public String createDocument(@ModelAttribute("form") DocumentForm form, @RequestParam String authorName) {
        System.out.println("form = " + form.toString());
        System.out.println("authorName = " + authorName);
        log.debug(form.toString());
        return "redirect:/home";
    }

}
