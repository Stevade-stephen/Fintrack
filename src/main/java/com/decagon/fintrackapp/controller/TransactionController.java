package com.decagon.fintrackapp.controller;

import com.decagon.fintrackapp.model.*;
import com.decagon.fintrackapp.payload.CategoryRequest;
import com.decagon.fintrackapp.payload.TransactionRequest;
import com.decagon.fintrackapp.serviceImp.requester_service.RequesterServiceImpl;
import com.decagon.fintrackapp.serviceImp.super_admin_service.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/fintrack")
public class TransactionController {


    private final TransactionServiceImpl transactionService;
    private final RequesterServiceImpl requesterService;

    @Autowired
    public TransactionController(TransactionServiceImpl transactionService, RequesterServiceImpl requesterService) {
        this.transactionService = transactionService;
        this.requesterService = requesterService;
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @PostMapping(value = {"/create_transaction"})
    public ResponseEntity<?> createTransaction(@RequestBody TransactionRequest transactionRequest){
        return requesterService.createTransaction(transactionRequest);
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @PostMapping(value = "/create_request-Category")
    public ResponseEntity<?> createRequestCategory(@RequestBody CategoryRequest categoryRequest){

        return transactionService.addRequestCategory(categoryRequest);
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @GetMapping(value = {"/view_transaction_ByCategory/{transactionCategory}"})
    public ResponseEntity<?> viewTransactionByCategory(@PathVariable(value = "transactionCategory") Optional<ECategory> transactionCategory)
                                                      {
        return transactionService.viewTransactions( transactionCategory, Optional.empty(), Optional.empty());

    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @GetMapping(value = {"/view_transaction_ByStatus/{transactionStatus}"})
    public ResponseEntity<?> viewTransactionByStatus(@PathVariable(value = "transactionStatus")Optional<EStatus> transactionStatus
                                                     ){
        return transactionService.viewTransactions( Optional.empty(), transactionStatus, Optional.empty());

    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @GetMapping(value = {"/view_all_transaction"})
    public ResponseEntity<?> viewAllTransaction(){
        return transactionService.viewTransactions( Optional.empty(), Optional.empty(), Optional.empty());

    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @GetMapping(value = {"/view_transaction_byStatus/{transactionCategory}/{transactionStatus}"})
    public ResponseEntity<?> viewTransactionByStatusAndCategory(@PathVariable(value = "transactionCategory") Optional<ECategory> transactionCategory,
                                                                @PathVariable(value = "transactionStatus")Optional<EStatus> transactionStatus){
        return transactionService.viewTransactions( transactionCategory, transactionStatus, Optional.empty());
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @GetMapping(value = {"/view_transaction_byECash/{transactionECashType}"})
    public ResponseEntity<?> viewTransactionByECashType(@PathVariable(value = "transactionECashType")Optional<ECashType> transactionECashType){
        return transactionService.viewTransactions(Optional.empty(), Optional.empty(), transactionECashType);
    }

//    @PostMapping(value =  {"/edit_transaction/{transactionId}"})
//    public ResponseEntity<?> editTransaction(@PathVariable Long transactionId, @RequestBody TransactionRequest transaction) throws URISyntaxException {
//
//        return requesterService.editTransaction(transactionId, transaction);
//    }
}
