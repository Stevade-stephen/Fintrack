package com.decagon.fintrackapp.serviceImp.super_admin_service;

import com.decagon.fintrackapp.exception.AppException;
import com.decagon.fintrackapp.model.*;
import com.decagon.fintrackapp.payload.ApiResponse;
import com.decagon.fintrackapp.payload.TransactionRequest;
import com.decagon.fintrackapp.repository.DepartmentRepository;
import com.decagon.fintrackapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class DepartmentImpl {

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    UserRepository userRepository;

    public ResponseEntity<?> createDepartment(String name) {

        if (departmentRepository.existsByName(name)){
            throw new AppException("Department already exist");
        }
        Department department1 = new Department();
        department1.setName(name);
        Department result = departmentRepository.save(department1);

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/department/{name}")
                .buildAndExpand(result.getName()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(
                true, "Department created successfully"));

    }


    public ResponseEntity<?> addMember(Long departmentId, Long userId) {

        Optional<Department> department = departmentRepository.findById(departmentId);
        if(department.isEmpty())throw new AppException("Department does not exist");
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) throw new AppException("User does not exist");

        if(department.get().getUsers().contains(user.get())) throw new AppException("User already exist");


        department.get().getUsers().add(user.get());
        user.get().setDepartment(department.get());
        departmentRepository.save(department.get());
        userRepository.save(user.get());
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/department/{name}")
                .buildAndExpand(department.get().getName()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(
                true, "Member added to department successfully"));
    }


    public ResponseEntity<?> removeMember(Long departmentId, Long userId) {
        Optional<Department> department = departmentRepository.findById(departmentId);

        if(department.isEmpty())throw new AppException("There is no department with id " + departmentId);

        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) throw new AppException("There is no user with  id " + userId);

        if(!department.get().getUsers().contains(user.get())) throw new AppException("User not in this department");

        department.get().getUsers().remove(user.get());
        user.get().setDepartment(null);
        departmentRepository.save(department.get());
        userRepository.save(user.get());
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/department/{name}")
                .buildAndExpand(department.get().getName()).toUri();
        return ResponseEntity.created(location).body(new ApiResponse(
                true, "Member successfully removed from department"));
    }
}
