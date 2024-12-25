package com.example.BankingApplicationSystem.repositorys;

import com.example.BankingApplicationSystem.entity.Feedback;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FeedbackRepository extends MongoRepository<Feedback, String> {
    List<Feedback> findByAccountId(String accountId);
}