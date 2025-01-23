package com.example.BankingApplicationSystem.services;

import com.example.BankingApplicationSystem.entity.BillPayment;
import com.example.BankingApplicationSystem.repositorys.BillPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BillPaymentService {

    @Autowired
    private BillPaymentRepository billPaymentRepository;

    public BillPayment addBill(BillPayment billPayment) {
        return billPaymentRepository.save(billPayment);
    }

    public List<BillPayment> getBillsByAccountId(String accountId) {
        return billPaymentRepository.findByAccountId(accountId);
    }

    public void markReminderAsSent(String billId) {
        BillPayment bill = billPaymentRepository.findById(billId).orElse(null);
        if (bill != null) {
            bill.setReminderSent(true); // Set reminderSent to true
            billPaymentRepository.save(bill); // Save the updated bill
        }
    }

    public List<BillPayment> getPendingReminders() {
        return billPaymentRepository.findByDueDateBeforeAndReminderSentFalse(LocalDateTime.now().plusDays(1));
    }
}