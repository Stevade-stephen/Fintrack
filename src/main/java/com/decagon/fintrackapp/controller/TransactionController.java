package com.decagon.fintrackapp.controller;

import com.decagon.fintrackapp.model.*;
import com.decagon.fintrackapp.payload.CategoryRequest;
import com.decagon.fintrackapp.payload.TransactionRequest;
import com.decagon.fintrackapp.serviceImp.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.Optional;

@RestController
public class TransactionController {

    @Autowired
    TransactionServiceImpl transactionService;


    @PostMapping(value = {"/create_transaction"})
    public ResponseEntity<?> createTransaction(@RequestBody TransactionRequest transactionRequest){
        return transactionService.createTransaction(transactionRequest);
    }

    @PostMapping(value = "/create_request-Category")
    public ResponseEntity<?> createRequestCategory(@RequestBody CategoryRequest categoryRequest){

        return transactionService.addRequestCategory(categoryRequest);
    }

    @GetMapping(value = {"/view_transaction_ByCategory/{transactionCategory}"})
    public ResponseEntity<?> viewTransactionByCategory(@PathVariable(value = "transactionCategory") Optional<ECategory> transactionCategory,
                                                       @PathVariable(value = "transactionStatus")Optional<EStatus> transactionStatus,
                                                       @PathVariable(value = "transactionECashType")Optional<ECashType> transactionECashType){
        return transactionService.viewTransactions( transactionCategory, transactionStatus, transactionECashType);

    }

    @GetMapping(value = {"/view_transaction_ByStatus/{transactionStatus}"})
    public ResponseEntity<?> viewTransactionByStatus(@PathVariable(value = "transactionCategory") Optional<ECategory> transactionCategory,
                                                     @PathVariable(value = "transactionStatus")Optional<EStatus> transactionStatus,
                                                     @PathVariable(value = "transactionECashType")Optional<ECashType> transactionECashType){
        return transactionService.viewTransactions( transactionCategory, transactionStatus, transactionECashType);

    }

    @GetMapping(value = {"/view_all_transaction"})
    public ResponseEntity<?> viewAllTransaction(@PathVariable(value = "transactionCategory") Optional<ECategory> transactionCategory,
                                                     @PathVariable(value = "transactionStatus")Optional<EStatus> transactionStatus,
                                                @PathVariable(value = "transactionECashType")Optional<ECashType> transactionECashType){
        return transactionService.viewTransactions( transactionCategory, transactionStatus, transactionECashType);

    }

    @GetMapping(value = {"/view_transaction_byStatus/{transactionCategory}/{transactionStatus}"})
    public ResponseEntity<?> viewTransactionByStatusAndCategory(@PathVariable(value = "transactionCategory") Optional<ECategory> transactionCategory,
                                                                @PathVariable(value = "transactionStatus")Optional<EStatus> transactionStatus,
                                                                @PathVariable(value = "transactionECashType")Optional<ECashType> transactionECashType){
        return transactionService.viewTransactions( transactionCategory, transactionStatus, transactionECashType);
    }

    @GetMapping(value = {"/view_transaction_byECash/{transactionECashType}"})
    public ResponseEntity<?> viewTransactionByECashType(@PathVariable(value = "transactionCategory") Optional<ECategory> transactionCategory,
                                                        @PathVariable(value = "transactionStatus")Optional<EStatus> transactionStatus,
                                                        @PathVariable(value = "transactionECashType")Optional<ECashType> transactionECashType){
        return transactionService.viewTransactions(transactionCategory, transactionStatus, transactionECashType);
    }

    @PostMapping(value =  {"/edit_transaction/{transactionId}"})
    public ResponseEntity<?> editTransaction(@PathVariable Long transactionId, @RequestBody TransactionRequest transaction) throws URISyntaxException {

        return transactionService.editTransaction(transactionId, transaction);
    }
}
