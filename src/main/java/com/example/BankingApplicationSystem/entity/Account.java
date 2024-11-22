package com.example.BankingApplicationSystem.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "accountdata")
@Data
public class Account {

    @Id
    private String id;
    private String accountHolderName;
    private String accountPassword;
    private String accountType;
    private double balance;
}
