package com.example.BankingApplicationSystem.repositorys;

import com.example.BankingApplicationSystem.entity.Dispute;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DisputeRepository extends MongoRepository<Dispute, String> {
    List<Dispute> findByAccountId(String accountId);
}