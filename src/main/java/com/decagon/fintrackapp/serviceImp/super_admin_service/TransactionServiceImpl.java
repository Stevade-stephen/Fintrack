package com.decagon.fintrackapp.serviceImp.super_admin_service;

import com.decagon.fintrackapp.exception.AppException;
import com.decagon.fintrackapp.model.*;
import com.decagon.fintrackapp.payload.ApiResponse;
import com.decagon.fintrackapp.payload.TransactionRequest;
import com.decagon.fintrackapp.payload.CategoryRequest;
import com.decagon.fintrackapp.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TransactionServiceImpl {

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    ClaimsCategoryRepository claimsCategoryRepository;
    @Autowired
    RequestCategoryRepository requestCategoryRepository;
    @Autowired
    UserRepository userRepository;
    //@Autowired
    //TransactionTypeRepository transactionTypeRepository;


    @SuppressWarnings({"unchecked", "rawtypes"})
    public ResponseEntity<?> addRequestCategory(CategoryRequest categoryRequest) {

        if (requestCategoryRepository.existsByName(categoryRequest.getCategoryName())) {
            return new ResponseEntity(
                    new ApiResponse(false, "There is already a category named " + categoryRequest.getCategoryName()),
                    HttpStatus.BAD_REQUEST);
        }

        List<User> approvalList = new ArrayList<>();

//        Optional<User> user = userRepository.getUserByRoles(ERole.LINE_MANAGER);
//        Optional<User> user1 = userRepository.getUserByRoles(ERole.FINANCIAL_CONTROLLER);
//        Optional<User> user2 = userRepository.getUserByRoles(ERole.CEO);

        RequestCategory requestCategory = new RequestCategory(categoryRequest.getCategoryName(), categoryRequest.getMin(), categoryRequest.getMax());
//        if (categoryRequest.getMax() <= 10000) {
//            requestCategory.getTransactionType().setECashType(ECashType.PETTY_CASH);
//            approvalList.add(user.get());
//            approvalList.add(user1.get());
//
//        }else{
//            requestCategory.getTransactionType().setECashType(ECashType.CASH_FOR_UPLOAD);
//            approvalList.add(user.get());
//            approvalList.add(user1.get());
//            approvalList.add(user2.get());
//        }
//            TransactionType transactionType = new TransactionType(categoryRequest.getDescription(),
//                    categoryRequest.getMin(), categoryRequest.getMax());
////            if (categoryRequest.getMax() <= 10000) {
//                transactionType.setECashType(ECashType.PETTY_CASH);
//            } else {
//                transactionType.setECashType(ECashType.CASH_FOR_UPLOAD);
//            }
//            requestCategory.setTransactionType(transactionType);


//        requestCategory.getTransactionType().setApprovalList(approvalList);

        RequestCategory result = requestCategoryRepository
                .save(requestCategory);

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/request-category/{id}")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true,
                "TransactionCategory has been added successfully!"));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public ResponseEntity<?> addClaimsCategory(ClaimsCategory category) {

        if (claimsCategoryRepository.existsByName(category.getName())) {
            return new ResponseEntity(
                    new ApiResponse(false, "Already there is category named " + category.getName()),
                    HttpStatus.BAD_REQUEST);
        }
        ClaimsCategory result = claimsCategoryRepository
                .save(new ClaimsCategory(category.getName(), category.getMin(), category.getMax()));

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/claims-category/{id}")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true,
                "TransactionCategory has been added successfully!"));
    }


    @SuppressWarnings({"unchecked", "rawtypes"})
    public ResponseEntity<?> viewTransactions(Optional<ECategory> category, Optional<EStatus> status,
                                              Optional<ECashType> eCashType) {

        if (status.isEmpty() && category.isEmpty() && eCashType.isEmpty()) {
            List<Transaction> transactions = transactionRepository.findAll();
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        }
        if (category.isEmpty() && eCashType.isEmpty()) {
            Optional<List<Transaction>> transactions = transactionRepository.findAllByStatus(status.get());
            return transactions.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity(new ApiResponse(false, "Transaction Category not found!"),
                    HttpStatus.BAD_REQUEST));
        }
        if (status.isEmpty() && eCashType.isEmpty()) {
            Optional<List<Transaction>> transactions = transactionRepository.findAllByCategory(category.get());
            return transactions.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity(new ApiResponse(false, "Transaction Status not found!"),
                    HttpStatus.BAD_REQUEST));
        }
        if (status.isEmpty() && category.isEmpty()) {
            Optional<List<Transaction>> transactions = transactionRepository.findAllByCashType(eCashType.get());
            return transactions.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity(new ApiResponse(false, "Transaction Type not found"),
                    HttpStatus.BAD_REQUEST));
        }

        Optional<List<Transaction>> transactions = transactionRepository.findAllByCategoryAndStatus(category.get(), status.get());
        return transactions.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity(new ApiResponse(false, "Specified transaction is not found!"),
                HttpStatus.BAD_REQUEST));
    }

    public ResponseEntity<?> notifyDisbursal(Long transactionId, Long userId){
        Optional<Transaction> transaction = transactionRepository.findById(transactionId);
        Long requesterId = transaction.get().getRequester().getId();
        if(userId.equals(requesterId)){
            if(transaction.get().getCashType().equals(ECashType.PETTY_CASH)){
                if(transaction.get().getApproval().getIsApprovedByLineManager().equals(EApproval.APPROVED)
                        && transaction.get().getApproval().getIsApprovedByFinancialController().equals(EApproval.APPROVED)){
                    transaction.get().setDisbursed(true);
                    transactionRepository.save(transaction.get());
                }
            } else{
                if(transaction.get().getApproval().getIsApprovedByLineManager().equals(EApproval.APPROVED) &&
                        transaction.get().getApproval().getIsApprovedByFinancialController().equals(EApproval.APPROVED)
                        && transaction.get().getApproval().getIsApprovedByCEO().equals(EApproval.APPROVED)){
                    transaction.get().setDisbursed(true);
                    transactionRepository.save(transaction.get());
                }
            }
            return new ResponseEntity<>(new ApiResponse(true, "Disbursal successfully notified"),
                    HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponse(false, "Unable to set disbursal notification"),
                HttpStatus.BAD_REQUEST);
    }
}
