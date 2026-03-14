package org.geeksforgeeks.digitalwallet.payment.bank;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/** Response from POST /api/bank/credit */
@Data
@NoArgsConstructor
public class BankCreditResponse {
    private boolean success;
    private String referenceId;
    private String transactionId;
    private BankTransactionStatus status;
    private BigDecimal amount;
    private String message;
    private String timestamp;
}
