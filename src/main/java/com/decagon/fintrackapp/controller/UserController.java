package com.decagon.fintrackapp.controller;

import com.decagon.fintrackapp.config.JwtTokenProvider;
import com.decagon.fintrackapp.model.ECategory;
import com.decagon.fintrackapp.model.EStatus;
import com.decagon.fintrackapp.model.User;
import com.decagon.fintrackapp.payload.ApiResponse;
import com.decagon.fintrackapp.payload.JwtAuthenticationResponse;
import com.decagon.fintrackapp.repository.UserRepository;
import com.decagon.fintrackapp.serviceImp.TransactionServiceImpl;
import com.decagon.fintrackapp.serviceImp.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RestController
public class UserController {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    UserRepository userRepository;

//    @Autowired
//    TransactionServiceImpl transactionService;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
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
    public ResponseEntity restricted(@AuthenticationPrincipal(expression = "claims['name']") String name,
                                  @AuthenticationPrincipal(expression = "claims") Map<String, Object> claims,
                                  @AuthenticationPrincipal(expression = "claims['preferred_username']") String email){
        claims.forEach((key, value) -> log.info(key+" "+value));
        Optional<User> oldUser = userRepository.getUserByEmail(email);
        User user = new User();
        if(oldUser.isEmpty()) {
            user.setName(name);
            user.setEmail(email);
            userService.addUser(user);

        }
        final String jwt = tokenProvider.generateToken(user);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }



}
