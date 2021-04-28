package com.decagon.fintrackapp.serviceImp;

import com.decagon.fintrackapp.config.WebSecurityAuditable;
import com.decagon.fintrackapp.exception.AppException;
import com.decagon.fintrackapp.model.*;
import com.decagon.fintrackapp.payload.ApiResponse;
import com.decagon.fintrackapp.repository.ApprovalRepository;
import com.decagon.fintrackapp.repository.TransactionRepository;
import com.decagon.fintrackapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.decagon.fintrackapp.model.ECashType.*;

@Service
public class ApprovalServiceImpl {

    private final TransactionRepository transactionRepository;
    private final WebSecurityAuditable webSecurityAuditable;
    private final UserRepository userRepository;
    private final ApprovalRepository approvalRepository;

    @Autowired
    public ApprovalServiceImpl(TransactionRepository transactionRepository, WebSecurityAuditable webSecurityAuditable, UserRepository userRepository, ApprovalRepository approvalRepository) {
        this.transactionRepository = transactionRepository;
        this.webSecurityAuditable = webSecurityAuditable;
        this.userRepository = userRepository;
        this.approvalRepository = approvalRepository;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public ResponseEntity<?> approveTransaction(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(() ->
                new AppException("No transaction Found with id " + transactionId));
        Optional<String> str = webSecurityAuditable.getCurrentAuditor();
        User currentAuditor = userRepository.findByName(str.get());
        System.err.println(currentAuditor.getName());



        if (!transaction.getApprovalList().contains(currentAuditor)) {
            throw new AppException("Not authorized.");
        }

        User requester = transaction.getRequester();
        Department department = requester.getDepartment();
        Company company = requester.getCompany();
        Approval approval = transaction.getApproval();


        if (currentAuditor.equals(department.getLineManager())) {

            approval.setApprovedByLineManager(true);
            approvalRepository.save(approval);

            User user = transaction.getApprovalList().get(1);
            User oldUser = userRepository.findById(user.getId()).get();
            oldUser.getApprovals().add(approval);
            userRepository.save(oldUser);

            transactionRepository.save(transaction);

            //TODO notification should be sent to financial controller here

            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        if (approval.isApprovedByLineManager()) {
            if (currentAuditor.equals(company.getFinancialController())) {
                approval.setApprovedByFinancialController(true);
                approvalRepository.save(approval);
                transactionRepository.save(transaction);

                if (transaction.getCashType().equals(CASH_FOR_UPLOAD)) {
                    User user = transaction.getApprovalList().get(2);
                    User oldUser = userRepository.findById(user.getId()).get();
                    oldUser.getApprovals().add(approval);
                    userRepository.save(oldUser);
                }
                return new ResponseEntity<>(true, HttpStatus.OK);
            }
            if (approval.isApprovedByFinancialController()) {

                if (transaction.getCashType().equals(CASH_FOR_UPLOAD) && currentAuditor.equals(company.getCompanyCeo())) {
                    approval.setApprovedByCEO(true);
                    approvalRepository.save(approval);
                    transactionRepository.save(transaction);
                    return new ResponseEntity<>(true, HttpStatus.OK);
                }
            }

        }

        return new ResponseEntity(new ApiResponse(false, "Transaction Category not found!"),
                HttpStatus.BAD_REQUEST);

    }

}
