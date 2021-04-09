package com.decagon.fintrackapp.repository;


import com.decagon.fintrackapp.model.EStatus;
import com.decagon.fintrackapp.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findAllByStatus(EStatus status);
}
