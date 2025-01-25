package com.example.BankingApplicationSystem.services;

import com.example.BankingApplicationSystem.entity.BillPayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BillReminderSchedulerService {

    @Autowired
    private BillPaymentService billPaymentService;

    @Scheduled(cron = "0 0 9 * * ?") // Every day at 9 AM
    public void sendBillReminders() {
        List<BillPayment> pendingReminders = billPaymentService.getPendingReminders();
        for (BillPayment bill : pendingReminders) {
            // Logic to send reminder (e.g., email or notification)
            System.out.println("Reminder: Your bill for " + bill.getBillName() + " of amount " + bill.getAmount() + " is due on " + bill.getDueDate());
            billPaymentService.markReminderAsSent(bill.getId()); // Mark reminder as sent
        }
    }
}