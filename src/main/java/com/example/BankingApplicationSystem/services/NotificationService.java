package com.example.BankingApplicationSystem.services;

import com.example.BankingApplicationSystem.entity.Account;
import com.example.BankingApplicationSystem.entity.Notification;
import com.example.BankingApplicationSystem.repositorys.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private EmailService emailService;

    public void checkAndNotify(Account account, double transactionAmount) {
        // Check for low balance
        if (account.getBalance() < 100) {
            sendNotification(account, "Your account balance is low: ₹" + account.getBalance());
        }

        // Check for large transaction
        if (transactionAmount > 10000) {
            sendNotification(account, "A large transaction of ₹" + transactionAmount + " has been made.");
        }
    }

    private void sendNotification(Account account, String message) {
        Notification notification = new Notification();
        notification.setAccountId(account.getId());
        notification.setMessage(message);
        notification.setCreatedAt(LocalDateTime.now());
        notificationRepository.save(notification);

        // Send email notification
        emailService.sendNotification(account.getEmail(), "Account Notification", message);
    }

    public List<Notification> getNotificationsByAccountId(String accountId) {
        return notificationRepository.findByAccountId(accountId);
    }
}