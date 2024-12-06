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
    private EmailService emailService; // Inject EmailService


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
        emailService.sendNotification(account.getEmail(), "Deposit Successful", "You have successfully deposited " + amount + ". Your new balance is " + account.getBalance());


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
        emailService.sendNotification(account.getEmail(), "Withdrawal Successful", "You have successfully withdrawn " + amount + ". Your new balance is " + account.getBalance());


        return savedTransaction;
    }
    public List<Transaction> findTransactionsByAccountId(String accountId) {
        return transactionRepository.findByAccountId(accountId);
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


        /// Send notification to sender
        emailService.sendNotification(senderAccount.getEmail(), "Transfer Successful", "You have successfully transferred " + amount + " to account " + recipientAccountId + ". Your new balance is " + senderAccount.getBalance());

        // Send notification to recipient (assuming you have the recipient's account)
        Account recipientAccounts = accountRepository.findById(recipientAccountId).orElseThrow(() -> new IllegalArgumentException("Recipient account not found."));
        emailService.sendNotification(recipientAccount.getEmail(), "Transfer Received", "You have received " + amount + " from account " + senderAccount.getAccountHolderName() + ". Your new balance is " + recipientAccounts.getBalance());

        // Return the sender's transaction or recipient's transaction based on your requirement
        return senderTransaction; // or return recipientTransaction based on your requirement
    }
}
