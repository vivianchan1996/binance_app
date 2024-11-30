package com.crypto.controller;

import com.crypto.model.TransactionHistory;
import com.crypto.repository.TransactionHistoryRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/history")
public class HistoryController {
    private final TransactionHistoryRepo transactionRepo;

    public HistoryController(TransactionHistoryRepo transactionRepo) {
        this.transactionRepo = transactionRepo;
    }

    @GetMapping("/allByUserId")
    public ResponseEntity<List<TransactionHistory>> getAllHistoryByUserId(Long userId) {
        List<TransactionHistory> history = transactionRepo.findByUserId(userId);
        return ResponseEntity.ok(history);
    }
}
