package com.decagon.fintrackapp.repository;

import com.decagon.fintrackapp.model.Department;
import com.decagon.fintrackapp.model.RequestCategory;
import com.decagon.fintrackapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    boolean existsByName(String name);

    Optional<Department> findUserById(Long userId);
}
