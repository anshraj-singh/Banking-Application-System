package com.example.BankingApplicationSystem.services;

import com.example.BankingApplicationSystem.entity.Account;
import com.example.BankingApplicationSystem.entity.Transaction; // Assuming you have a Transaction entity
import com.example.BankingApplicationSystem.repositorys.AccountRepository;
import com.example.BankingApplicationSystem.repositorys.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AccountActivitySummaryService {

    @Autowired
    private AccountRepository accountRepository; // Assuming you have an AccountRepository
    @Autowired
    private TransactionRepository transactionRepository; // Assuming you have a TransactionRepository
    @Autowired
    private EmailService emailService;

    // Scheduled task to run weekly
    @Scheduled(cron = "0 0 12 * * MON") // Every Monday at noon
    public void sendWeeklyAccountActivitySummary() {
        List<Account> accounts = accountRepository.findAll(); // Get all accounts
        for (Account account : accounts) {
            String email = account.getEmail(); // Assuming Account has an email field
            String summary = generateAccountActivitySummary(account);
            emailService.sendNotification(email, "Weekly Account Activity Summary", summary);
        }
    }

    // Method to generate account activity summary
    private String generateAccountActivitySummary(Account account) {
        StringBuilder summary = new StringBuilder();
        summary.append("Account Activity Summary for ").append(account.getAccountHolderName()).append("\n");
        summary.append("Account ID: ").append(account.getId()).append("\n");
        summary.append("Period: ").append(LocalDate.now().minusDays(7)).append(" to ").append(LocalDate.now()).append("\n\n");

        List<Transaction> transactions = transactionRepository.findByAccountIdAndDateBetween(
                account.getId(),
                LocalDate.now().minusDays(7),
                LocalDate.now()
        );

        if (transactions.isEmpty()) {
            summary.append("No transactions in this period.");
        } else {
            for (Transaction transaction : transactions) {
                summary.append("Date: ").append(transaction.getTransactionDate)
                        .append(", Amount: ").append(transaction.getAmount())
                        .append(", Description: ").append(transaction.getDescription()).append("\n");
            }
        }

        return summary.toString();
    }
}