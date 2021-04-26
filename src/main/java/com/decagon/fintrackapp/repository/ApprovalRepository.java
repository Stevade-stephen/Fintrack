package com.decagon.fintrackapp.repository;

import com.decagon.fintrackapp.model.Approval;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalRepository extends JpaRepository<Approval, Long> {

}
