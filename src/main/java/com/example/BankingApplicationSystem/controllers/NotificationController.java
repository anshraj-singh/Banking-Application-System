package com.example.BankingApplicationSystem.controllers;

import com.example.BankingApplicationSystem.entity.Notification;
import com.example.BankingApplicationSystem.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public List<Notification> getNotifications(@RequestParam String accountId) {
        return notificationService.getNotificationsByAccountId(accountId);
    }
}