package com.decagon.fintrackapp.serviceImp;

import com.decagon.fintrackapp.model.*;
import com.decagon.fintrackapp.payload.ApiResponse;
import com.decagon.fintrackapp.payload.TransactionRequest;
import com.decagon.fintrackapp.payload.CategoryRequest;
import com.decagon.fintrackapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransactionServiceImpl {

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    ClaimsCategoryRepository claimsCategoryRepository;
    @Autowired
    RequestCategoryRepository requestCategoryRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TransactionTypeRepository transactionTypeRepository;


    @SuppressWarnings({"unchecked", "rawtypes"})
    public ResponseEntity<?> addRequestCategory(CategoryRequest categoryRequest) {

        if (requestCategoryRepository.existsByName(categoryRequest.getCategoryName())) {
            return new ResponseEntity(
                    new ApiResponse(false, "Already there is category named " + categoryRequest.getCategoryName()),
                    HttpStatus.BAD_REQUEST);
        }

        List<User> approvalList = new ArrayList<>();

        Optional<User> user = userRepository.getUserByRoles(ERole.LINE_MANAGER);
        Optional<User> user1 = userRepository.getUserByRoles(ERole.FINANCIAL_CONTROLLER);
        Optional<User> user2 = userRepository.getUserByRoles(ERole.CEO);

        RequestCategory requestCategory = new RequestCategory(categoryRequest.getCategoryName());
        if(categoryRequest.getMax() <= 10000){
            requestCategory.getTransactionType().setECashType(ECashType.PETTY_CASH);
            approvalList.add(user.get());
            approvalList.add(user1.get());

        }else{
            requestCategory.getTransactionType().setECashType(ECashType.CASH_FOR_UPLOAD);
            approvalList.add(user.get());
            approvalList.add(user1.get());
            approvalList.add(user2.get());
        }
        requestCategory.getTransactionType().setDescription(categoryRequest.getDescription());


        requestCategory.getTransactionType().setApprovalList(approvalList);

        RequestCategory result = requestCategoryRepository
                .save(requestCategory);

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/request-category/{id}")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true,
                "TransactionCategory has been added successfully!"));
    }

    public ResponseEntity<?> addTransactionType(RequestCategory requestCategory, String description) {

        if (transactionRepository.existsByName(requestCategory.getName())) {
            return new ResponseEntity(
                    new ApiResponse(false, "Already there is category named " + requestCategory.getName()),
                    HttpStatus.BAD_REQUEST);
        }

       TransactionType transactionType = new TransactionType();
        transactionType.setECashType(requestCategory.getTransactionType().getECashType());
        transactionType.setDescription(description);
        transactionType.setApprovalList(requestCategory.getTransactionType().getApprovalList());

        TransactionType result = transactionTypeRepository.save(transactionType);

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
                .save(new ClaimsCategory(category.getName()));

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/claims-category/{id}")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true,
                "TransactionCategory has been added successfully!"));
    }


    @SuppressWarnings({"unchecked", "rawtypes"})
    public ResponseEntity<?> viewTransactions(Optional<ECategory> category, Optional<EStatus> status) {

        if (status.isEmpty() && category.isEmpty()) {
            List<Transaction> transactions = transactionRepository.findAll();
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        }
        if (category.isEmpty()){
            Optional<List<Transaction>> transactions = transactionRepository.findAllByStatus(status.get());
            return transactions.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity(new ApiResponse(false, "Transaction Category not found!"),
                    HttpStatus.BAD_REQUEST));
        }
        if(status.isEmpty()){
            Optional<List<Transaction>> transactions = transactionRepository.findAllByCategory(category.get());
            return transactions.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity(new ApiResponse(false, "Transaction Status not found!"),
                    HttpStatus.BAD_REQUEST));
        }
        
        Optional<List<Transaction>> transactions = transactionRepository.findAllByCategoryAndStatus(category.get(), status.get());
        return transactions.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity(new ApiResponse(false, "Specified transaction is not found!"),
                HttpStatus.BAD_REQUEST));
    }


    public ResponseEntity<?> createTransaction(TransactionRequest transactionRequest) {
        Transaction transaction = new Transaction(transactionRequest.getTitle(), transactionRequest.getDescription(),
                transactionRequest.getAmount(), transactionRequest.getReceiptUrls(), transactionRequest.getCategory());

        transaction.setStatus(EStatus.PENDING);

        Transaction result = transactionRepository.save(transaction);

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/transaction/{title}")
                .buildAndExpand(result.getTitle()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(
                true, "transaction added successfully"));

    }
}
