package com.example.BankingApplicationSystem.controllers;

import com.example.BankingApplicationSystem.entity.Account;
import com.example.BankingApplicationSystem.entity.Transaction;
import com.example.BankingApplicationSystem.services.AccountService;
import com.example.BankingApplicationSystem.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/statements")
public class AccountStatementController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/me")
    public ResponseEntity<byte[]> getAccountStatement(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        // Parse the date strings to LocalDateTime
        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);

        // Get the authenticated user's account
        String accountHolderName = SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = accountService.findByUserName(accountHolderName);

        if (account == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Retrieve transactions for the specified date range
        List<Transaction> transactions = transactionService.findTransactionsByAccountIdAndDateRange(account.getId(), start, end);

        // Generate the PDF or CSV file
        byte[] report = generateAccountStatement(transactions);

        // Set headers for the response
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=account_statement.pdf");

        return new ResponseEntity<>(report, headers, HttpStatus.OK);
    }

    private byte[] generateAccountStatement(List<Transaction> transactions) {
        // Implement PDF or CSV generation logic here
        // For simplicity, let's assume we are generating a CSV file
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        StringBuilder csvBuilder = new StringBuilder();
        csvBuilder.append("Transaction ID,Account ID,Type,Amount,Date\n");

        for (Transaction transaction : transactions) {
            csvBuilder.append(transaction.getId()).append(",")
                    .append(transaction.getAccountId()).append(",")
                    .append(transaction.getType()).append(",")
                    .append(transaction.getAmount()).append(",")
                    .append(transaction.getTransactionDate()).append("\n");
        }

        return csvBuilder.toString().getBytes();
    }
}