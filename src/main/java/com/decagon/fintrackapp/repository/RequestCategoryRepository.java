package com.decagon.fintrackapp.repository;

import com.decagon.fintrackapp.model.RequestCategory;
import com.decagon.fintrackapp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestCategoryRepository extends JpaRepository<RequestCategory, Long> {
    Boolean existsByName(String name);
}
