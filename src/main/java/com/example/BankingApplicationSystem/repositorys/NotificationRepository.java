package com.example.BankingApplicationSystem.repositorys;

import com.example.BankingApplicationSystem.entity.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NotificationRepository extends MongoRepository<Notification, String> {
    List<Notification> findByAccountId(String accountId);
}