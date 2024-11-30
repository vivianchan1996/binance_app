package com.crypto.repository;

import com.crypto.model.UserWalletBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserWalletBalanceRepo extends JpaRepository<UserWalletBalance, Long> {
    List<UserWalletBalance> findByUserId(Long userId);
}

