package com.decagon.fintrackapp.repository;

import com.decagon.fintrackapp.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionTypeRepository extends JpaRepository<TransactionType, Long> {

}
