package com.example.BankingApplicationSystem.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "bill_payments")
@Data
public class BillPayment {
    @Id
    private String id;
    private String accountId; // Reference to the user's account
    private String billName; // Name of the bill (e.g., Electricity, Water)
    private double amount; // Amount due
    private LocalDateTime dueDate; // Due date for the bill
    private boolean reminderSent; // Flag to check if reminder has been sent
}