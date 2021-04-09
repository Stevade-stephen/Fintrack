package com.decagon.fintrackapp.repository;

import com.decagon.fintrackapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    Boolean existsByName(String name);

    User findByName(String username);
    Optional<User> getUserByEmail(String email);
}
