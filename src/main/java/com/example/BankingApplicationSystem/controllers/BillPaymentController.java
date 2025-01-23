package com.example.BankingApplicationSystem.controllers;

import com.example.BankingApplicationSystem.entity.BillPayment;
import com.example.BankingApplicationSystem.services.BillPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bills")
public class BillPaymentController {

    @Autowired
    private BillPaymentService billPaymentService;

    @PostMapping
    public ResponseEntity<BillPayment> addBill(@RequestBody BillPayment billPayment) {
        BillPayment savedBill = billPaymentService.addBill(billPayment);
        return new ResponseEntity<>(savedBill, HttpStatus.CREATED);
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<BillPayment>> getBillsByAccountId(@PathVariable String accountId) {
        List<BillPayment> bills = billPaymentService.getBillsByAccountId(accountId);
        return new ResponseEntity<>(bills, HttpStatus.OK);
    }
}