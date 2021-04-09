package com.decagon.fintrackapp.repository;

import com.decagon.fintrackapp.model.ClaimsCategory;
import com.decagon.fintrackapp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClaimsCategoryRepository extends JpaRepository<ClaimsCategory, Long> {
    Boolean existsByName(String name);
}
