package com.example.BankingApplicationSystem.repositorys;

import com.example.BankingApplicationSystem.entity.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
    List<Transaction> findByAccountId(String accountId);
}
