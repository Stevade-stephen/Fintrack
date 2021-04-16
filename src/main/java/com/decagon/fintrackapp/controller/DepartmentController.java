package com.decagon.fintrackapp.controller;

import com.decagon.fintrackapp.serviceImp.super_admin_service.DepartmentImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/fintrack")
public class DepartmentController {


    private final DepartmentImpl departmentService;

    @Autowired
    public DepartmentController(DepartmentImpl departmentService) {
        this.departmentService = departmentService;
    }
    @PostMapping(value = {"/create_department/{name}"})
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<?> createDepartment(@PathVariable(value="name") String name){
        return departmentService.createDepartment(name);
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @PostMapping(value =  {"department/add_member/{departmentId}/{userId}"})
    public ResponseEntity<?> addMember(@PathVariable(value = "departmentId") Long departmentId,
                                       @PathVariable(value = "userId") Long userId){
        return departmentService.addMember(departmentId,userId);
    }

}
