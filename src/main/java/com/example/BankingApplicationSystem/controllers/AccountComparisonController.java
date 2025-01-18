package com.example.BankingApplicationSystem.controllers;

import com.example.BankingApplicationSystem.entity.Account;
import com.example.BankingApplicationSystem.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/account-comparison")
public class AccountComparisonController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public List<Account> getAccountComparison() {
        List<Account> accountTypes = new ArrayList<>();

        // Example account types with features
        Account savingsAccount = new Account();
        savingsAccount.setAccountType("Savings");
        savingsAccount.setInterestRate(4.0);
        savingsAccount.setFeatures("Low risk, interest-bearing account, suitable for saving money.");
        accountTypes.add(savingsAccount);

        Account checkingAccount = new Account();
        checkingAccount.setAccountType("Checking");
        checkingAccount.setInterestRate(1.0);
        checkingAccount.setFeatures("Flexible account for daily transactions, usually with no interest.");
        accountTypes.add(checkingAccount);

        Account fixedDepositAccount = new Account();
        fixedDepositAccount.setAccountType("Fixed Deposit");
        fixedDepositAccount.setInterestRate(6.0);
        fixedDepositAccount.setFeatures("Higher interest rate for funds locked in for a fixed term.");
        accountTypes.add(fixedDepositAccount);

        return accountTypes;
    }
}