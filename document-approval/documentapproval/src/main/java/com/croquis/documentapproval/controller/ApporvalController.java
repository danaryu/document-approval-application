package com.croquis.documentapproval.controller;

import com.croquis.documentapproval.domain.Document;
import com.croquis.documentapproval.domain.DocumentApproval;
import com.croquis.documentapproval.service.ApprovalService;
import com.croquis.documentapproval.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/approval")
@RequiredArgsConstructor
public class ApporvalController {

    private final ApprovalService approvalService;
    private final DocumentService documentService;

    @GetMapping("/inbox")
    public String findInbox(@AuthenticationPrincipal User user, Model model) {
        List<DocumentApproval> inboxList = approvalService.findInbox(user.getUsername());
        model.addAttribute("inboxList", inboxList);
        return "approvals/inbox";
    }

    // 결재 승인
    @GetMapping("{approvalId}/approve")
    public String createApproveForm(@AuthenticationPrincipal User user,
                                    Model model,
                                    @PathVariable("approvalId") Long approvalId) {
        model.addAttribute("approvalId", approvalId);
        return "approvals/approveForm";
    }

    @PostMapping("/approve")
    public String apporoveDocument(@AuthenticationPrincipal User user,
                                   Model model,
                                   @RequestParam("approvalId") Long approvalId,
                                   @RequestParam("comment") String comment) {
        approvalService.approveDocument(approvalId, comment);
        return "redirect:/home";
    }

    // 결재 반려
    @GetMapping("{approvalId}/return")
    public String createReturnForm(@AuthenticationPrincipal User user,
                                   Model model,
                                   @PathVariable("approvalId") Long approvalId) {
        model.addAttribute("approvalId", approvalId);
        return "approvals/returnForm";
    }

    @PostMapping("/return")
    public String returnDocument(@AuthenticationPrincipal User user,
                                 Model model,
                                 @RequestParam("approvalId") Long approvalId,
                                 @RequestParam("comment") String comment) {
        approvalService.returnDocument(approvalId, comment);
        return "redirect:/home";
    }

    @GetMapping("/archive")
    public String findArchive(@AuthenticationPrincipal User user, Model model) {
        List<Document> archiveList = approvalService.findArchive(user.getUsername());
        model.addAttribute("archiveList", archiveList);
        return "approvals/archive";
    }

    @GetMapping("/outbox")
    public String findOutbox(@AuthenticationPrincipal User user, Model model) {
        List<Document> outboxList = approvalService.findOutbox(user.getUsername());
        model.addAttribute("outboxList", outboxList);
        return "approvals/outbox";
    }

}
