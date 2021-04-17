package com.decagon.fintrackapp.controller;

import com.decagon.fintrackapp.config.JwtTokenProvider;
import com.decagon.fintrackapp.model.ERole;
import com.decagon.fintrackapp.model.User;
import com.decagon.fintrackapp.payload.JwtAuthenticationResponse;
import com.decagon.fintrackapp.repository.UserRepository;
import com.decagon.fintrackapp.serviceImp.super_admin_service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping(value = "/fintrack")
public class UserController {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    UserRepository userRepository;

//    @Autowired
//    TransactionServiceImpl transactionService;



   // @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @PostMapping(value = {"/assign_roles/{roleId}/{userId}"})
    public ResponseEntity<?> assignRole(@PathVariable(value="roleId") Set<Long> roleId,
                                        @PathVariable(value="userId") Long userId){
        return userService.assignRole(roleId, userId);
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @PostMapping(value = {"/remove_roles/{roleId}/{userId}"})
    public ResponseEntity<?> removeRole(@PathVariable(value="roleId") Set<Long> roleId,
                                        @PathVariable(value="userId") Long userId){
        return userService.removeUserRole(roleId, userId);
    }

    @GetMapping("/home")
    public ResponseEntity<?> restricted(@AuthenticationPrincipal(expression = "claims['name']") String name,
                                  @AuthenticationPrincipal(expression = "claims") Map<String, Object> claims,
                                  @AuthenticationPrincipal(expression = "claims['preferred_username']") String email){
        System.out.println("I got here");
        return userService.createUser(name, email);
    }
}
