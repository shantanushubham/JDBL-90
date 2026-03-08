package org.geeksforgeeks.digitalwallet.controller;

import lombok.RequiredArgsConstructor;
import org.geeksforgeeks.digitalwallet.domain.entity.Transaction;
import org.geeksforgeeks.digitalwallet.domain.entity.User;
import org.geeksforgeeks.digitalwallet.dto.AddMoneyRequest;
import org.geeksforgeeks.digitalwallet.dto.SendMoneyRequest;
import org.geeksforgeeks.digitalwallet.dto.TransferToBankRequest;
import org.geeksforgeeks.digitalwallet.service.SendMoneyResult;
import org.geeksforgeeks.digitalwallet.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.geeksforgeeks.digitalwallet.dto.ErrorResponse;
import org.geeksforgeeks.digitalwallet.dto.TransactionResponse;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/add-money")
    public ResponseEntity<?> addMoney(
            @AuthenticationPrincipal User user,
            @RequestBody AddMoneyRequest request) {
        try {
            Transaction transaction = this.transactionService.addMoney(user, request);
            return ResponseEntity.ok(this.buildTransactionResponse(transaction));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @PostMapping("/send-money")
    public ResponseEntity<?> sendMoney(
            @AuthenticationPrincipal User user,
            @RequestBody SendMoneyRequest request) {
        try {
            SendMoneyResult result = this.transactionService.sendMoney(user, request);
            TransactionResponse response = this.buildTransactionResponse(result.getTransaction());
            if (result.getRewardAmount() != null) {
                response.setRewardNote("🎉 You've earned a reward of ₹" + result.getRewardAmount() + "! It will be credited to your wallet shortly.");
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @PostMapping("/transfer-to-bank")
    public ResponseEntity<?> transferToBank(
            @AuthenticationPrincipal User user,
            @RequestBody TransferToBankRequest request) {
        try {
            Transaction transaction = this.transactionService.transferToBank(user, request);
            return ResponseEntity.ok(this.buildTransactionResponse(transaction));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    private TransactionResponse buildTransactionResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .transactionId(transaction.getId())
                .status(transaction.getStatus())
                .type(transaction.getType())
                .amount(transaction.getAmount())
                .description(transaction.getDescription())
                .timestamp(transaction.getCreatedAt())
                .build();
    }
}
