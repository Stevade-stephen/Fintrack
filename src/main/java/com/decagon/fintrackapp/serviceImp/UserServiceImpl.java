package com.decagon.fintrackapp.serviceImp;

import com.decagon.fintrackapp.exception.AppException;
import com.decagon.fintrackapp.model.Role;
import com.decagon.fintrackapp.model.User;
import com.decagon.fintrackapp.payload.ApiResponse;
import com.decagon.fintrackapp.repository.RoleRepository;
import com.decagon.fintrackapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    public ResponseEntity<?> assignRole(Set<Long> roleId, Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) throw new AppException("User does not exist");
        Set<Role> role = roleId.stream()
                .map(id ->roleRepository.findById(id).get()).collect(Collectors.toSet());
        role.addAll(user.get().getRoles());
        user.get().setRoles(role);
        userRepository.save(user.get());
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(user.get().getName()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(
                true, "Role added successfully"));
    }

    public void addUser(User user) {
        userRepository.save(user);
    }


    public ResponseEntity<?> removeUserRole(Set<Long> roleId, Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) throw new AppException("User does not exist");
        Set<Role> role = roleId.stream()
                .map(id ->roleRepository.findById(id).get()).collect(Collectors.toSet());
        user.get().getRoles().removeAll(role);
        userRepository.save(user.get());
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(user.get().getName()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(
                true, "Role(s) removed successfully"));

    }
}
