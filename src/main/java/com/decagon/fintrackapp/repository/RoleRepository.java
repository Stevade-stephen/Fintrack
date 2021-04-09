package com.decagon.fintrackapp.repository;

import com.decagon.fintrackapp.model.ERole;
import com.decagon.fintrackapp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByAppUserRole(ERole name);
}
