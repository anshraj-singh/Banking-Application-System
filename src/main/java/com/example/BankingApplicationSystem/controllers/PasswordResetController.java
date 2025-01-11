package com.example.BankingApplicationSystem.controllers;

import com.example.BankingApplicationSystem.entity.Account;
import com.example.BankingApplicationSystem.services.AccountService;
import com.example.BankingApplicationSystem.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/password-reset")
public class PasswordResetController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private EmailService emailService;

    // Request password reset
    @PostMapping("/request")
    public ResponseEntity<String> requestPasswordReset(@RequestParam String accountHolderName) {
        Account account = accountService.findByUserName(accountHolderName);
        if (account == null) {
            return new ResponseEntity<>("Account not found", HttpStatus.NOT_FOUND);
        }

        // Generate a reset token and expiration time
        String resetToken = UUID.randomUUID().toString();
        account.setResetToken(resetToken);
        account.setResetTokenExpiration(LocalDateTime.now().plusHours(1)); // Token valid for 1 hour
        accountService.saveUpdateAccount(account);

        // Send email with reset link
        String resetLink = "http://yourdomain.com/reset-password?token=" + resetToken;
        emailService.sendPasswordResetEmail(account.getEmail(), resetLink);

        return new ResponseEntity<>("Password reset link sent to your email", HttpStatus.OK);
    }

    // Reset password
    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        Account account = accountService.findByResetToken(token);
        if (account == null || account.getResetTokenExpiration().isBefore(LocalDateTime.now())) {
            return new ResponseEntity<>("Invalid or expired token", HttpStatus.BAD_REQUEST);
        }

        // Update the password
        account.setAccountPassword(accountService.getPasswordEncoder().encode(newPassword));
        accountService.saveUpdateAccount(account);

        // Send email notification after password reset
        String subject = "Your Password Has Been Updated";
        String body = "Hello " + account.getAccountHolderName() + ",\n\n" +
                "Your password has been successfully updated.\n" +
                "Your new password is: " + newPassword + "\n\n" +
                "If you did not request this change, please contact support.";
        emailService.sendNotification(account.getEmail(), subject, body);


        return new ResponseEntity<>("Password has been reset successfully", HttpStatus.OK);
    }
}