package com.decagon.fintrackapp.repository;


import com.decagon.fintrackapp.model.ECategory;
import com.decagon.fintrackapp.model.EStatus;
import com.decagon.fintrackapp.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<List<Transaction>> findAllByStatus(EStatus status);
    Optional<List<Transaction>> findAllByCategoryAndStatus(ECategory category, EStatus status);
    Optional<List<Transaction>> findAllByCategory(ECategory category);
    Boolean existsByName(String name);
}
