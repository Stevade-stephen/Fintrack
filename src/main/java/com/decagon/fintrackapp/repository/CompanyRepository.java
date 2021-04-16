package com.decagon.fintrackapp.repository;

import com.decagon.fintrackapp.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {

}
