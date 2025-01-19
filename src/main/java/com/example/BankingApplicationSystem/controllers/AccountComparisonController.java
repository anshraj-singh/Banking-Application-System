package com.example.BankingApplicationSystem.controllers;

import com.example.BankingApplicationSystem.entity.Account;
import com.example.BankingApplicationSystem.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/account-comparison")
public class AccountComparisonController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public List<Account> getAccountComparison() {
        // Retrieve all accounts from the service
        List<Account> accounts = accountService.findAllAccount();

        // Populate account details for comparison
        for (Account account : accounts) {
            switch (account.getAccountType().toLowerCase()) {
                case "savings":
                    account.setInterestRate(4.0);
                    account.setFeatures("Low risk, interest-bearing account, suitable for saving money.");
                    break;
                case "checking":
                    account.setInterestRate(1.0);
                    account.setFeatures("Flexible account for daily transactions, usually with no interest.");
                    break;
                case "fixed deposit":
                    account.setInterestRate(6.0);
                    account.setFeatures("Higher interest rate for funds locked in for a fixed term.");
                    break;
                default:
                    account.setInterestRate(0.0);
                    account.setFeatures("No specific features available.");
                    break;
            }
        }

        return accounts;
    }
}