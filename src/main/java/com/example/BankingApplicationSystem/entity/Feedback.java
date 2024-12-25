package com.example.BankingApplicationSystem.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "feedback")
@Data
public class Feedback {
    @Id
    private String id;
    private String accountId; // Reference to the account providing feedback
    private String message; // Feedback message
    private LocalDateTime createdAt; // Timestamp of feedback submission
}