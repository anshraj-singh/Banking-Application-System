package com.example.BankingApplicationSystem.services;

import com.example.BankingApplicationSystem.entity.Account;
import com.example.BankingApplicationSystem.repositorys.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private EmailService emailService;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Account saveAccount(Account account) {
        account.setAccountPassword(passwordEncoder.encode(account.getAccountPassword()));
        account.setCreatedAt(LocalDateTime.now()); // Set the current date and time
        account.setRoles(Arrays.asList("USER"));

        // Set default interest rate based on account type
        setInterestRate(account);

        // Save the account to the database
        Account savedAccount = accountRepository.save(account);

        // Send confirmation email
        String subject = "Welcome to Our Banking Application!";
        String message = "Dear " + savedAccount.getAccountHolderName() + ",\n\n" +
                "Thank you for creating an account with us! Your account has been successfully created.\n" +
                "Your account details are as follows:\n" +
                "Account ID: " + savedAccount.getId() + "\n" +
                "Account Type: " + savedAccount.getAccountType() + "\n" +
                "Interest Rate: " + savedAccount.getInterestRate() + "%\n\n" +
                "Thank you for choosing our services!\n\n" +
                "Best Regards,\n" +
                "The Banking Application Team";

        emailService.sendNotification(savedAccount.getEmail(), subject, message);

        return savedAccount;
    }

    public void setInterestRate(Account account) {
        switch (account.getAccountType().toLowerCase()) {
            case "savings":
                account.setInterestRate(4.0); // Example interest rate for savings account
                break;
            case "checking":
                account.setInterestRate(1.0); // Example interest rate for checking account
                break;
            case "fixed deposit":
                account.setInterestRate(6.0); // Example interest rate for fixed deposit
                break;
            default:
                account.setInterestRate(0.0); // Default interest rate
                break;
        }
    }

    public void saveUpdateAccount(Account account){
        accountRepository.save(account);
    }

    public void saveAdmin(Account account) {
        account.setAccountPassword(passwordEncoder.encode(account.getAccountPassword()));
        account.setCreatedAt(LocalDateTime.now()); // Set the current date and time
        account.setRoles(Arrays.asList("USER","ADMIN"));
        accountRepository.save(account);
    }

    public List<Account> findAllAccount() {
        return accountRepository.findAll();
    }

    public Optional<Account> findByIdAccount(String myId) {
        return accountRepository.findById(myId);
    }

    public void deleteByIdAccount(String myId) {
        accountRepository.deleteById(myId);
    }

    public Account findByUserName(String userName){
        return accountRepository.findByAccountHolderName(userName);
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public Account findByResetToken(String resetToken) {
        return accountRepository.findByResetToken(resetToken);
    }
}