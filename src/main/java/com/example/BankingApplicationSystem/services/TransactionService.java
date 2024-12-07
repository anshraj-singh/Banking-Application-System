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

    @Autowired
    private EmailService emailService;

    //! deposit money logic service
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

        // Send notification email
        String subject = "Deposit Successful";
        String message = "Dear " + account.getAccountHolderName() + ",\n\n" +
                "You have successfully deposited ₹" + amount + " into your account.\n" +
                "Your new balance is ₹" + account.getBalance() + ".\n\n" +
                "Thank you for banking with us!";
        emailService.sendNotification(account.getEmail(), subject, message);

        return savedTransaction;
    }

    //! withdraw money logic service
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

        // Send notification email
        String subject = "Withdrawal Successful";
        String message = "Dear " + account.getAccountHolderName() + ",\n\n" +
                "You have successfully withdrawn ₹" + amount + " from your account.\n" +
                "Your new balance is ₹" + account.getBalance() + ".\n\n" +
                "Thank you for banking with us!";
        emailService.sendNotification(account.getEmail(), subject, message);

        return savedTransaction;
    }

    //! transfer amount to another account
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

        // Create transaction for sender
        Transaction senderTransaction = new Transaction();
        senderTransaction.setAccountId(senderAccount.getId());
        senderTransaction.setType("TRANSFER_OUT");
        senderTransaction.setAmount(amount);
        senderTransaction.setTransactionDate(LocalDateTime.now());
        transactionRepository.save(senderTransaction);

        // Add sender's transaction to sender's account
        senderAccount.getTransactions().add(senderTransaction);
        accountRepository.save(senderAccount);

        // Create transaction for recipient
        Transaction recipientTransaction = new Transaction();
        recipientTransaction.setAccountId(recipientAccount.getId());
        recipientTransaction.setType("TRANSFER_IN");
        recipientTransaction.setAmount(amount);
        recipientTransaction.setTransactionDate(LocalDateTime.now());
        transactionRepository.save(recipientTransaction);

        // Add recipient's transaction to recipient's account
        recipientAccount.getTransactions().add(recipientTransaction);
        accountRepository.save(recipientAccount);

        // Send notification email to sender
        String senderSubject = "Transfer Successful";
        String senderMessage = "Dear " + senderAccount.getAccountHolderName() + ",\n\n" +
                "You have successfully transferred ₹" + amount + " to account ID: " + recipientAccountId + ".\n" +
                "Your new balance is ₹" + senderAccount.getBalance() + ".\n\n" +
                "Thank you for banking with us!";
        emailService.sendNotification(senderAccount.getEmail(), senderSubject, senderMessage);

        // Send notification email to recipient
        String recipientSubject = "Transfer Received";
        String recipientMessage = "Dear " + recipientAccount.getAccountHolderName() + ",\n\n" +
                "You have received ₹" + amount + " from " + senderAccount.getAccountHolderName() + ".\n" +
                "Your new balance is ₹" + recipientAccount.getBalance() + ".\n\n" +
                "Thank you for banking with us!";
        emailService.sendNotification(recipientAccount.getEmail(), recipientSubject, recipientMessage);

        return senderTransaction;
    }

    public List<Transaction> findTransactionsByAccountId(String accountId) {
        return transactionRepository.findByAccountId(accountId);
    }
}
