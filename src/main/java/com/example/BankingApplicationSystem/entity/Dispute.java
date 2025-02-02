package com.example.BankingApplicationSystem.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "disputes")
@Data
public class Dispute {
    @Id
    private String id;
    private String transactionId; // Reference to the disputed transaction
    private String accountId; // Reference to the user's account
    private String reason; // Reason for the dispute
    private String status; // Status of the dispute (e.g., "PENDING", "RESOLVED", "CLOSED")
    private LocalDateTime createdAt; // Timestamp of when the dispute was created
}