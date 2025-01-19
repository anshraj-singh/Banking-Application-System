package com.example.BankingApplicationSystem.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "accountdata")
@Data
public class Account {

    @Id
    private String id;
    private String accountHolderName;
    private String accountPassword;
    private String accountType; // e.g., "Savings", "Checking", "Fixed Deposit"
    private double balance;
    private double interestRate; // Interest rate for the account type
    private LocalDateTime createdAt; // Creation date and time
    private String email; // User email
    private List<String> roles; // USER OR ADMIN
    @DBRef
    private List<Transaction> transactions = new ArrayList<>(); // List of transactions for the account

    // New fields for transaction limits
    private double dailyTransactionLimit;
    private double weeklyTransactionLimit;
    private double monthlyTransactionLimit;

    // New fields for password reset
    private String resetToken;
    private LocalDateTime resetTokenExpiration;

    // New field for account features
    private String features; // Description of features
}