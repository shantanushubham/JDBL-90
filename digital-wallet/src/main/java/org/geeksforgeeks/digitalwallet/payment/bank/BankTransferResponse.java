package org.geeksforgeeks.digitalwallet.payment.bank;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/** Response from POST /api/bank/transfer */
@Data
@NoArgsConstructor
public class BankTransferResponse {
    private boolean success;
    private String referenceId;
    private String transactionId;
    private BankTransactionStatus status;
    private TransferMode mode;
    private BigDecimal amount;
    private String estimatedSettlement;
    private String message;
    private String timestamp;
}
