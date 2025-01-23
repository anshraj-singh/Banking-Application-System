package com.example.BankingApplicationSystem.repositorys;

import com.example.BankingApplicationSystem.entity.BillPayment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BillPaymentRepository extends MongoRepository<BillPayment, String> {

    // Find all bills by account ID
    List<BillPayment> findByAccountId(String accountId);

    // Find bills that are due before a certain date and have not had reminders sent
    List<BillPayment> findByDueDateBeforeAndReminderSentFalse(LocalDateTime dueDate);
}