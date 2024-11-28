package com.example.BankingApplicationSystem.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "transactiondata")
@Data
public class Transaction {

    @Id
    private String id;
    private String accountId; // Reference to the account performing the transaction
    private String type; // "DEPOSIT" or "WITHDRAWAL"
    private double amount;
    private LocalDateTime transactionDate; // Timestamp of the transaction
}
