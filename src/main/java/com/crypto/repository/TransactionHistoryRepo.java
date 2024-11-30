package com.crypto.repository;

import com.crypto.model.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionHistoryRepo extends JpaRepository<TransactionHistory, Long> {
    List<TransactionHistory> findByUserId(Long userId);
}

