package com.example.wallet.repository;

import com.example.wallet.entity.Transaction;
import com.example.wallet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t WHERE t.sender = :user OR t.receiver = :user ORDER BY t.createdAt DESC")
    List<Transaction> findByUserOrderByCreatedAtDesc(@Param("user") User user);

    @Query("SELECT t FROM Transaction t WHERE t.id = :id AND (t.sender.id = :userId OR t.receiver.id = :userId)")
    Optional<Transaction> findByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);
}
