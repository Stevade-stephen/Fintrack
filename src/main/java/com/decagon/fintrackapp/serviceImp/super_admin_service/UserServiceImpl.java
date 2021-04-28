package com.decagon.fintrackapp.serviceImp.super_admin_service;

import com.decagon.fintrackapp.config.JwtTokenProvider;
import com.decagon.fintrackapp.exception.AppException;
import com.decagon.fintrackapp.model.*;
import com.decagon.fintrackapp.payload.ApiResponse;
import com.decagon.fintrackapp.payload.JwtAuthenticationResponse;
import com.decagon.fintrackapp.payload.LoginRequest;
import com.decagon.fintrackapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.decagon.fintrackapp.model.ERole.*;


@Service
public class UserServiceImpl {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    JwtTokenProvider tokenProvider;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    DepartmentRepository departmentRepository;


    @SuppressWarnings({"unchecked", "rawtypes"})
    public ResponseEntity<?> createUser(String name, String email) {
        Optional<User> oldUser = userRepository.getUserByEmail(email);
        User user = new User();
        final String jwt;
        if (oldUser.isEmpty()) {
            user.setName(name);
            user.setEmail(email);
            Optional<Company> company = companyRepository.findById(1L);
            user.setCompany(company.get());
            Optional<Role> role = roleRepository.findByAppUserRole(REQUESTER);
            user.setRoles(Set.of(role.get()));
            userRepository.save(user);
            jwt = tokenProvider.generateToken(user);
        } else {
            jwt = tokenProvider.generateToken(oldUser.get());

        }
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    public ResponseEntity<?> assignRole(Set<Long> roleId, Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) {
            throw new AppException("User does not exist");
        }
        Set<Role> role = roleId.stream()
                .map(id ->roleRepository.findById(id).get()).collect(Collectors.toSet());
        Role lineManger = roleRepository.findByAppUserRole(LINE_MANAGER).get();
        Role companyCEO = roleRepository.findByAppUserRole(CEO).get();
        Role financialController = roleRepository.findByAppUserRole(FINANCIAL_CONTROLLER).get();

        role.addAll(user.get().getRoles());
        user.get().setRoles(role);

        if(role.contains(lineManger)) {
            user.get().getDepartment().setLineManager(user.get());

        }
        if(role.contains(companyCEO)){
            user.get().getCompany().setCompanyCeo(user.get());
        }
        if(role.contains(financialController)){
            user.get().getCompany().setFinancialController(user.get());
        }

        userRepository.save(user.get());
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(user.get().getName()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(
                true, "Role added successfully"));
    }


    public ResponseEntity<?> removeUserRole(Set<Long> roleId, Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) throw new AppException("User does not exist");


        if(user.get().getDepartment() == null) {
            Set<Role> role = roleId.stream()
                    .map(id ->roleRepository.findById(id).get()).collect(Collectors.toSet());
            user.get().getRoles().removeAll(role);
            userRepository.save(user.get());

        } else {
            Optional<Department> department = departmentRepository.findById(user.get().getDepartment().getId());
            Set<Role> role = roleId.stream()
                .map(id ->roleRepository.findById(id).get()).collect(Collectors.toSet());
            user.get().getRoles().removeAll(role);

            userRepository.save(user.get());
            Role thisRole = new Role(LINE_MANAGER);

            if(role.contains(thisRole))
                department.get().setLineManager(null);
            departmentRepository.save(department.get());
        }


        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(user.get().getName()).toUri();
        return ResponseEntity.created(location).body(new ApiResponse(
                true, "Role(s) removed successfully"));

    }

    public ResponseEntity<?> updateTransactionStatus(Long transactionId){
        Transaction transaction = transactionRepository.findById(transactionId).get();
        if(transaction.isDisbursed()){
            transaction.setStatus(EStatus.DISBURSED);
           transactionRepository.save(transaction);

            return new ResponseEntity<>(new ApiResponse(true, "Status set to Disbursed"),
                    HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponse(false, "Unable to set Status"),
                HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> uploadReceipt(Long transactionId, Long userId, String receiptUrl) {
        Transaction transaction = transactionRepository.findById(transactionId).get();
        Long requesterId = transaction.getRequester().getId();
        if(requesterId.equals(userId)){
            if(transaction.isDisbursed()) {
                transaction.setReceiptUrls(receiptUrl);
                transactionRepository.save(transaction);
                return new ResponseEntity<>(new ApiResponse(true, "Receipt uploaded successfully"),
                        HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(new ApiResponse(false, "Could not upload receipt"),
                HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> closeTransaction(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId).get();
        if(transaction.getReceiptUrls() != null){
            transaction.setStatus(EStatus.CLOSED);
            transactionRepository.save(transaction);
            return new ResponseEntity<>(new ApiResponse(true, "Transaction Closed successfully"),
                    HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponse(false, "Could not close transaction"),
                HttpStatus.BAD_REQUEST);
    }
}
