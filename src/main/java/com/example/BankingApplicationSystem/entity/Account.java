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
    private String accountType; // New field for account type (e.g., savings, checking, fixed deposit)
    private double balance;
    private double interestRate; // New field for interest rate
    private LocalDateTime createdAt; // New field to store creation date and time
    private String email; // New field to store user email
    private List<String> roles; //! USER OR ADMIN
    @DBRef
    private List<Transaction> transactions = new ArrayList<>(); // List of transactions for the account

    // New fields for password reset
    private String resetToken;
    private LocalDateTime resetTokenExpiration;
}