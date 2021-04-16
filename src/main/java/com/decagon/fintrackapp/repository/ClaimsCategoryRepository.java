package com.decagon.fintrackapp.repository;

import com.decagon.fintrackapp.model.ClaimsCategory;
import com.decagon.fintrackapp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClaimsCategoryRepository extends JpaRepository<ClaimsCategory, Long> {
    Boolean existsByName(String name);
    ClaimsCategory findClaimsCategoriesByName (ClaimsCategory claimsCategory);
}
