package com.decagon.fintrackapp.controller;

import com.decagon.fintrackapp.model.ECategory;
import com.decagon.fintrackapp.model.EStatus;
import com.decagon.fintrackapp.model.RequestCategory;
import com.decagon.fintrackapp.payload.TransactionRequest;
import com.decagon.fintrackapp.serviceImp.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

public class TransactionController {

    @Autowired
    TransactionServiceImpl transactionService;


    @PostMapping(value = {"create_transaction/{transactionRequest}"})
    public ResponseEntity<?> createTransaction(@PathVariable(value = "transactionRequest")TransactionRequest transactionRequest){
        return transactionService.createTransaction(transactionRequest);
    }

    @PostMapping(value = "create_request-Category")
    public ResponseEntity<?> createRequestCategory(@PathVariable()){

        return transactionService.
    }

    @GetMapping(value = {"view_transaction_byStatus/{transactionCategory}/{transactionStatus}"})
    public ResponseEntity<?> viewTransactionByStatus(@PathVariable(value = "transactionCategory") Optional<ECategory> transactionCategory,
                                                     @PathVariable(value = "transactionStatus")Optional<EStatus> transactionStatus){
        return transactionService.viewTransactions( transactionCategory, transactionStatus);

    }
}
