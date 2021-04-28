package com.decagon.fintrackapp.controller;

import com.decagon.fintrackapp.config.JwtTokenProvider;
import com.decagon.fintrackapp.exception.AppException;
import com.decagon.fintrackapp.model.ERole;
import com.decagon.fintrackapp.model.Role;
import com.decagon.fintrackapp.model.User;
import com.decagon.fintrackapp.repository.RoleRepository;
import com.decagon.fintrackapp.serviceImp.super_admin_service.DepartmentImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping(value = "/fintrack")
public class DepartmentController {

    private final JwtTokenProvider jwtTokenProvider;

    private final DepartmentImpl departmentService;
    private final RoleRepository roleRepository;


    @Autowired
    public DepartmentController(JwtTokenProvider jwtTokenProvider, DepartmentImpl departmentService, RoleRepository roleRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.departmentService = departmentService;
        this.roleRepository = roleRepository;
    }

//    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @PostMapping(value = {"/create_department/{name}"})
    public ResponseEntity<?> createDepartment(@PathVariable(value="name") String name, HttpServletRequest request) {
//        User user = jwtTokenProvider.resolveUser(request).orElseThrow(() ->
//                new AppException("User not found"));
//        log.info(String.valueOf(user));
//        Role role = roleRepository.findByAppUserRole(ERole.SUPER_ADMIN).orElseThrow(() -> new AppException("Role not found"));
//        if (user.getRoles().contains(role))
            return departmentService.createDepartment(name);
//        else throw new AppException("Access Denied");
    }

//    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @PostMapping(value =  {"/add_member/{departmentId}/{userId}"})
    public ResponseEntity<?> addMember(@PathVariable(value = "departmentId") Long departmentId,
                                       @PathVariable(value = "userId") Long userId){
        return departmentService.addMember(departmentId,userId);
    }

    @PostMapping(value =  {"/remove_member_from_department/{departmentId}/{userId}"})
    public ResponseEntity<?> removeMember(@PathVariable(value = "departmentId") Long departmentId,
                                          @PathVariable(value = "userId") Long userId){
        return departmentService.removeMember(departmentId,userId);
    }

}
