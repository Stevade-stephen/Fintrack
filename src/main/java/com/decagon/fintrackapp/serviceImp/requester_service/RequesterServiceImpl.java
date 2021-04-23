package com.decagon.fintrackapp.serviceImp.requester_service;


import com.decagon.fintrackapp.WebSecurityAuditable;
import com.decagon.fintrackapp.model.*;
import com.decagon.fintrackapp.payload.ApiResponse;
import com.decagon.fintrackapp.payload.TransactionRequest;
import com.decagon.fintrackapp.repository.DepartmentRepository;
import com.decagon.fintrackapp.repository.TransactionRepository;
import com.decagon.fintrackapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static com.decagon.fintrackapp.model.ECashType.*;

@Service
public class RequesterServiceImpl {

    private final WebSecurityAuditable webSecurityAuditable;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public RequesterServiceImpl(WebSecurityAuditable webSecurityAuditable, UserRepository userRepository,
                                TransactionRepository transactionRepository,
                                DepartmentRepository departmentRepository) {
        this.webSecurityAuditable = webSecurityAuditable;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.departmentRepository = departmentRepository;
    }


    @SuppressWarnings({"unchecked", "rawtypes"})
    public ResponseEntity<?> createTransaction(TransactionRequest transactionRequest) {
        Transaction transaction = new Transaction(transactionRequest.getTitle(), transactionRequest.getDescription(),
                transactionRequest.getAmount(), transactionRequest.getReceiptUrls(), transactionRequest.getCategory());

        transaction.setStatus(EStatus.PENDING);

        Optional <String>userName = webSecurityAuditable.getCurrentAuditor();
        User currentAuditor = userRepository.findByName(userName.get());
        transaction.setRequester(currentAuditor);
        Department department = currentAuditor.getDepartment();
        Company company =  currentAuditor.getCompany();


        if (transactionRequest.getAmount() <= 10000) {
            transaction.setCashType(PETTY_CASH);
            transaction.setApprovalList(List.of(department.getLineManager(), company.getFinancialController()));


        } else {
            transaction.setCashType(CASH_FOR_UPLOAD);
            transaction.setApprovalList(List.of(department.getLineManager(), company.getFinancialController(), company.getCompanyCeo()));
        }
//            requestCategory.setTransactionType(transactionType);


        Transaction result = transactionRepository.save(transaction);

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/transaction/{title}")
                .buildAndExpand(result.getTitle()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(
                true, "transaction added successfully"));

    }

//    @SuppressWarnings({"unchecked", "rawtypes"})
//    public ResponseEntity<?> editTransaction(Long id, TransactionRequest transactionRequest ) {
//        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new AppException("Transaction does not exist"));
////        log.info(transaction.toString());
//        transaction.setAmount(transactionRequest.getAmount());
//        transaction.setCategory(transactionRequest.getCategory());
//        transaction.setDescription(transactionRequest.getDescription());//
//        transaction.setReceiptUrls(transactionRequest.getReceiptUrls());
//        transaction.setTitle(transactionRequest.getTitle());
////        transaction.setStatus(EStatus.PENDING);
////
////        if (transactionRequest.getAmount() <= 10000) {
////            transaction.setCashType(ECashType.PETTY_CASH);
////        } else {
////            transaction.setCashType(ECashType.CASH_FOR_UPLOAD);
////        }
//////        transaction1.setLastModifiedDate(transaction.getLastModifiedDate());
//
////        log.info("I'm here");
//
//        transaction.setStatus(EStatus.PENDING);
//
//        if (transactionRequest.getAmount() <= 10000) {
//            transaction.setCashType(ECashType.PETTY_CASH);
//        } else {
//            transaction.setCashType(ECashType.CASH_FOR_UPLOAD);
//        }
////            requestCategory.setTransactionType(transactionType);
//
//
//        Transaction result = transactionRepository.save(transaction);
////        log.info(result.toString());
////        log.info("im here");
//
//        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/transaction/{title}")
//                .buildAndExpand(result.getTitle()).toUri();
//
//        return ResponseEntity.created(location).body(new ApiResponse(
//                true, "transaction updated successfully"));
//
//    }
}
