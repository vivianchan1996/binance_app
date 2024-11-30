package com.crypto.repository;

import com.crypto.model.UserWalletBalance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserWalletBalanceRepo extends JpaRepository<UserWalletBalance, Long> {
}

