package com.decagon.fintrackapp.controller;

import com.decagon.fintrackapp.serviceImp.ApprovalServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @PostMapping("/approve_transaction/{id}")
    public ResponseEntity<?> approveTransaction (@PathVariable Long id){
        return approvalService.approveTransaction(id);
    }

}
