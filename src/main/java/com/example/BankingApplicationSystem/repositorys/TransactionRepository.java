package com.example.BankingApplicationSystem.repositorys;

import com.example.BankingApplicationSystem.entity.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
    List<Transaction> findByAccountId(String accountId);
    List<Transaction> findByAccountIdAndTransactionDateBetween(String accountId, LocalDateTime startDate, LocalDateTime endDate);
    List<Transaction> findByAccountIdAndDateBetween(String id, LocalDate localDate, LocalDate now);
}

