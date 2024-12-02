package com.example.BankingApplicationSystem.services;

import com.example.BankingApplicationSystem.entity.Account;
import com.example.BankingApplicationSystem.entity.Transaction;
import com.example.BankingApplicationSystem.repositorys.AccountRepository;
import com.example.BankingApplicationSystem.repositorys.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    public Transaction deposit(Account account, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than zero.");
        }

        // Update account balance
        account.setBalance(account.getBalance() + amount);

        // Create transaction
        Transaction transaction = new Transaction();
        transaction.setAccountId(account.getId());
        transaction.setType("DEPOSIT");
        transaction.setAmount(amount);
        transaction.setTransactionDate(LocalDateTime.now());

        // Save transaction
        Transaction savedTransaction = transactionRepository.save(transaction);

        // Add transaction to the account
        account.getTransactions().add(savedTransaction);
        accountRepository.save(account);

        return savedTransaction;
    }

    public Transaction withdraw(Account account, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be greater than zero.");
        }

        if (account.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient funds for withdrawal.");
        }

        // Update account balance
        account.setBalance(account.getBalance() - amount);

        // Create transaction
        Transaction transaction = new Transaction();
        transaction.setAccountId(account.getId());
        transaction.setType("WITHDRAWAL");
        transaction.setAmount(amount);
        transaction.setTransactionDate(LocalDateTime.now());

        // Save transaction
        Transaction savedTransaction = transactionRepository.save(transaction);

        // Add transaction to the account
        account.getTransactions().add(savedTransaction);
        accountRepository.save(account);

        return savedTransaction;
    }

    public List<Transaction> findTransactionsByAccountId(String accountId) {
        return transactionRepository.findByAccountId(accountId);
    }

    //! transfer amount
    public Transaction transfer(Account senderAccount, String recipientAccountId, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be greater than zero.");
        }

        if (senderAccount.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient funds for transfer.");
        }

        // Find recipient account
        Account recipientAccount = accountRepository.findById(recipientAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Recipient account not found."));

        // Update sender's balance
        senderAccount.setBalance(senderAccount.getBalance() - amount);
        accountRepository.save(senderAccount);

        // Update recipient's balance
        recipientAccount.setBalance(recipientAccount.getBalance() + amount);
        accountRepository.save(recipientAccount);

        // Create transactions for both accounts
        Transaction senderTransaction = new Transaction();
        senderTransaction.setAccountId(senderAccount.getId());
        senderTransaction.setType("TRANSFER_OUT");
        senderTransaction.setAmount(amount);
        senderTransaction.setTransactionDate(LocalDateTime.now());
        transactionRepository.save(senderTransaction);

        Transaction recipientTransaction = new Transaction();
        recipientTransaction.setAccountId(recipientAccount.getId());
        recipientTransaction.setType("TRANSFER_IN");
        recipientTransaction.setAmount(amount);
        recipientTransaction.setTransactionDate(LocalDateTime.now());
        transactionRepository.save(recipientTransaction);

        return senderTransaction; // or return recipientTransaction based on your requirement
    }
}
