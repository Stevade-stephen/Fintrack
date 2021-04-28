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

            approval.setIsApprovedByLineManager(EApproval.APPROVED);
            approvalRepository.save(approval);

            User user = transaction.getApprovalList().get(1);
            User oldUser = userRepository.findById(user.getId()).get();
            oldUser.getApprovals().add(approval);
            System.err.println("Am here now");
            userRepository.save(oldUser);

            transactionRepository.save(transaction);

            System.err.println("Am here then");
            transactionRepository.save(transaction);
            System.err.println("Am not here");

            //TODO notification should be sent to financial controller here

            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        if (approval.getIsApprovedByLineManager().equals(EApproval.APPROVED)) {
            if (currentAuditor.equals(company.getFinancialController())) {
                approval.setIsApprovedByFinancialController(EApproval.APPROVED);
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
            if (approval.getIsApprovedByFinancialController().equals(EApproval.APPROVED)) {

                if (transaction.getCashType().equals(CASH_FOR_UPLOAD) && currentAuditor.equals(company.getCompanyCeo())) {
                    approval.setIsApprovedByCEO(EApproval.APPROVED);
                    approvalRepository.save(approval);
                    transactionRepository.save(transaction);
                    return new ResponseEntity<>(true, HttpStatus.OK);
                }
            }

        }

        return new ResponseEntity(new ApiResponse(false, "Transaction Category not found!"),
                HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<?> declineTransaction(Long id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() ->
                new AppException("No transaction Found with id " + id));
        Optional currentAuditor1 = webSecurityAuditable.getCurrentAuditor();
        User currentAuditor = userRepository.findByName(currentAuditor1.get().toString());


//        if (!transaction.getApprovalList().contains(currentAuditor)) {
//            throw new AppException("Not authorized.");
//        }

        User requester = transaction.getRequester();
        Department department = requester.getDepartment();
        Company company = requester.getCompany();
        Approval approval = transaction.getApproval();

        if (currentAuditor.equals(department.getLineManager())) {

            approval.setIsApprovedByLineManager(EApproval.DECLINED);
            approvalRepository.save(approval);
            transaction.setStatus(EStatus.CLOSED);
            transactionRepository.save(transaction);

            //TODO notification should be sent to financial controller here

            return new ResponseEntity<>(true, HttpStatus.OK);
        }

        if (approval.getIsApprovedByLineManager().equals(EApproval.APPROVED)) {
            if (currentAuditor.equals(company.getFinancialController())) {
                approval.setIsApprovedByFinancialController(EApproval.DECLINED);
                approvalRepository.save(approval);
                transaction.setStatus(EStatus.CLOSED);
                transactionRepository.save(transaction);

                return new ResponseEntity<>(true, HttpStatus.OK);
            }

            if (approval.getIsApprovedByFinancialController().equals(EApproval.APPROVED)) {
                if (transaction.getCashType().equals(CASH_FOR_UPLOAD) && currentAuditor.equals(company.getCompanyCeo())) {
                    approval.setIsApprovedByCEO(EApproval.DECLINED);
                    approvalRepository.save(approval);
                    transaction.setStatus(EStatus.CLOSED);
                    transactionRepository.save(transaction);
                    return new ResponseEntity<>(true, HttpStatus.OK);
                }
            }

        }
        return new ResponseEntity<>(new ApiResponse(false, "Transaction Category not found!"),
                HttpStatus.BAD_REQUEST);

    }
}
