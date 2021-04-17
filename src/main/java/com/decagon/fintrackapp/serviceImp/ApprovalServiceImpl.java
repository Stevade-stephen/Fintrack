package com.decagon.fintrackapp.serviceImp;

import com.decagon.fintrackapp.exception.AppException;
import com.decagon.fintrackapp.model.*;
import com.decagon.fintrackapp.payload.ApiResponse;
import com.decagon.fintrackapp.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static com.decagon.fintrackapp.model.ECashType.*;
import static com.decagon.fintrackapp.model.EStatus.*;

@Service
public class ApprovalServiceImpl {

    private final TransactionRepository transactionRepository;
    private final WebSecurityAuditable webSecurityAuditable;

    @Autowired
    public ApprovalServiceImpl(TransactionRepository transactionRepository, WebSecurityAuditable webSecurityAuditable) {
        this.transactionRepository = transactionRepository;
        this.webSecurityAuditable = webSecurityAuditable;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public ResponseEntity<?> approveTransaction(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(() ->
                new AppException("No transaction Found with id " + transactionId));
        Optional<User> currentAuditor = webSecurityAuditable.getCurrentAuditor();

        if (!transaction.getApprovalList().contains(currentAuditor.get())) {
            throw new AppException("Not authorized.");
        }

        User requester = transaction.getRequester();
        Department department = requester.getDepartment();
        User lineManager = department.getLineManager();
        Company company = requester.getCompany();
        User financialController = company.getFinancialController();
        Approval approval = transaction.getApproval();
        if (currentAuditor.get().equals(lineManager)) {
            approval.setApprovedByLineManager(true);
            //TODO notification should be sent to financial controller here
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        if (approval.isApprovedByLineManager()) {
            if (currentAuditor.get().equals(financialController)) {
                approval.setApprovedByFinancialController(true);

                if (transaction.getCashType().equals(CASH_FOR_UPLOAD)) {
                    //TODO notification should be sent to CEO here
                }
                return new ResponseEntity<>(true, HttpStatus.OK);
            }
            if (approval.isApprovedByFinancialController()) {

                if (transaction.getCashType().equals(CASH_FOR_UPLOAD) && currentAuditor.get().equals(company.getCompanyCeo())) {
                    approval.setApprovedByCEO(true);
                    return new ResponseEntity<>(true, HttpStatus.OK);
                }
            }

        }

        return new ResponseEntity(new ApiResponse(false, "Transaction Category not found!"),
                HttpStatus.BAD_REQUEST);

    }

}
