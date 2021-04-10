package com.decagon.fintrackapp.repository;


import com.decagon.fintrackapp.model.ECategory;
import com.decagon.fintrackapp.model.EStatus;
import com.decagon.fintrackapp.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<List<Transaction>> findAllByStatus(EStatus status);
    Optional<List<Transaction>> findAllByCategoryAndStatus(ECategory category, EStatus status);
    Optional<List<Transaction>> findAllByCategory(ECategory category);
    Boolean existsByTitle(String title);
}
