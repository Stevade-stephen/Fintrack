package com.decagon.fintrackapp.controller;

import com.decagon.fintrackapp.serviceImp.ApprovalServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fintrack")
public class ApprovalController {

    private final ApprovalServiceImpl approvalService;

    @Autowired
    public ApprovalController(ApprovalServiceImpl approvalService) {
        this.approvalService = approvalService;
    }

//    @PreAuthorize("hasAnyAuthority('LINE_MANAGER', 'FINANCIAL_CONTROLLER', 'CEO')")
    @PostMapping("/approve_transaction/{id}")
    public ResponseEntity<?> approveTransaction (@PathVariable Long id){
        return approvalService.approveTransaction(id);
    }

    @PostMapping("/decline_transaction/{id}")
    public ResponseEntity<?> declineTransaction (@PathVariable Long id){
        return approvalService.declineTransaction(id);
    }

}
