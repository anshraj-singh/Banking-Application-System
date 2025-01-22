package com.example.BankingApplicationSystem.controllers;

import com.example.BankingApplicationSystem.entity.Account;
import com.example.BankingApplicationSystem.entity.Transaction;
import com.example.BankingApplicationSystem.services.AccountService;
import com.example.BankingApplicationSystem.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/Transactions")
public class TransactionController {


    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    //! Deposit money into account
    @PostMapping("/me/deposit")
    public ResponseEntity<String> deposit(@RequestParam double amount) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String accountHolderName = authentication.getName();
        Account account = accountService.findByUserName(accountHolderName);

        if (account == null) {
            return new ResponseEntity<>("Account not found", HttpStatus.NOT_FOUND);
        }

        Transaction transaction = transactionService.deposit(account, amount);
        return new ResponseEntity<>("Deposit successful. Transaction ID: " + transaction.getId(), HttpStatus.OK);
    }

    //! Withdraw money from account
    @PostMapping("/me/withdraw")
    public ResponseEntity<String> withdraw(@RequestParam double amount) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String accountHolderName = authentication.getName();
        Account account = accountService.findByUserName(accountHolderName);

        if (account == null) {
            return new ResponseEntity<>("Account not found", HttpStatus.NOT_FOUND);
        }

        try {
            Transaction transaction = transactionService.withdraw(account, amount);
            return new ResponseEntity<>("Withdrawal successful. Transaction ID: " + transaction.getId(), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //! Get all transactions for authenticated user
    @GetMapping("/me/transactions")
    public ResponseEntity<List<Transaction>> getMyTransactions() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String accountHolderName = authentication.getName();
        Account account = accountService.findByUserName(accountHolderName);

        if (account == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Transaction> transactions = transactionService.findTransactionsByAccountId(account.getId());
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @PostMapping("/me/transfer")
    public ResponseEntity<String> transfer(@RequestParam String recipientAccountId, @RequestParam double amount) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String accountHolderName = authentication.getName();
        Account senderAccount = accountService.findByUserName(accountHolderName);

        if (senderAccount == null) {
            return new ResponseEntity<>("Account not found", HttpStatus.NOT_FOUND);
        }

        try {
            Transaction transaction = transactionService.transfer(senderAccount, recipientAccountId, amount);
            return new ResponseEntity<>("Transfer successful. Transaction ID: " + transaction.getId(), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // New endpoint for searching transaction history by date
    @GetMapping("/search")
    public ResponseEntity<List<Transaction>> searchTransactionsByDate(
            @RequestParam String accountId,
            @RequestParam String startDate,
            @RequestParam String endDate) {

        // Parse the date strings to LocalDateTime
        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);

        // Retrieve transactions for the specified date range
        List<Transaction> transactions = transactionService.findTransactionsByAccountIdAndDateRange(accountId, start, end);

        if (transactions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
}
