package com.decagon.fintrackapp.serviceImp;

import com.decagon.fintrackapp.model.ClaimsCategory;
import com.decagon.fintrackapp.model.EStatus;
import com.decagon.fintrackapp.model.RequestCategory;
import com.decagon.fintrackapp.model.Transaction;
import com.decagon.fintrackapp.payload.ApiResponse;
import com.decagon.fintrackapp.payload.CategoryDto;
import com.decagon.fintrackapp.payload.TransactionRequest;
import com.decagon.fintrackapp.repository.ClaimsCategoryRepository;
import com.decagon.fintrackapp.repository.RequestCategoryRepository;
import com.decagon.fintrackapp.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class TransactionServiceImpl {

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    ClaimsCategoryRepository claimsCategoryRepository;
    @Autowired
    RequestCategoryRepository requestCategoryRepository;

    @SuppressWarnings({"unchecked", "rawtypes"})
    public ResponseEntity<?> addRequestCategory(RequestCategory category) {

        if (requestCategoryRepository.existsByName(category.getName())) {
            return new ResponseEntity(
                    new ApiResponse(false, "Already there is category named " + category.getName()),
                    HttpStatus.BAD_REQUEST);
        }

        RequestCategory result = requestCategoryRepository
                .save(new RequestCategory(category.getName()));

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
    public ResponseEntity<?> viewTransactions(Optional<EStatus> status) {

        if (status.isEmpty()) {
            List<Transaction> transactions = transactionRepository.findAll();
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        }
        Optional<Transaction> transactions = transactionRepository.findAllByStatus(status.get());
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
