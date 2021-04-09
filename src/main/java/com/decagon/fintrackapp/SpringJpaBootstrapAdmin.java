package com.decagon.fintrackapp;


import com.decagon.fintrackapp.model.ERole;
import com.decagon.fintrackapp.model.Role;
import com.decagon.fintrackapp.model.User;
import com.decagon.fintrackapp.repository.RoleRepository;
import com.decagon.fintrackapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

/**
 * Adds the first
 * user as admin
 * to database
 */


//@Configuration
@Component
public class SpringJpaBootstrapAdmin implements ApplicationListener<ContextRefreshedEvent> {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SpringJpaBootstrapAdmin(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        loadFirstUserAsAdmin();
    }

    private void loadFirstUserAsAdmin() {
        Optional<Role> role = roleRepository.findByAppUserRole(ERole.SUPER_ADMIN);
        if (role.isEmpty()) {
            User superAdmin = new User();
            superAdmin.setName("temporalAdmin");
            superAdmin.setPassword(passwordEncoder.encode("admin"));
            superAdmin.setEmail("temp@Admin");

            Role role1 = new Role();
            Role role2 = new Role();
            Role role3 = new Role();
            Role role4 = new Role();
            Role role5 = new Role();


            role1.setAppUserRole(ERole.CEO);
            role2.setAppUserRole(ERole.SUPER_ADMIN);
            role3.setAppUserRole(ERole.REQUESTER);
            role4.setAppUserRole(ERole.FINANCIAL_CONTROLLER);
            role5.setAppUserRole(ERole.LINE_MANAGER);

            roleRepository.save(role1);
            roleRepository.save(role2);
            roleRepository.save(role3);
            roleRepository.save(role4);
            roleRepository.save(role5);

            superAdmin.setRoles(Set.of(role1, role2, role3));
            userRepository.save(superAdmin);
        }
    }
}
