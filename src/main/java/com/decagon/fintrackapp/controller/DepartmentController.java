package com.decagon.fintrackapp.controller;

import com.decagon.fintrackapp.serviceImp.DepartmentImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DepartmentController {

    @Autowired
    DepartmentImpl departmentService;

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @PostMapping(value = {"/create_department/{name}"})
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
