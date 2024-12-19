package com.example.BankingApplicationSystem.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "notifications")
@Data
public class Notification {
    @Id
    private String id;
    private String accountId;
    private String message;
    private LocalDateTime createdAt;
}