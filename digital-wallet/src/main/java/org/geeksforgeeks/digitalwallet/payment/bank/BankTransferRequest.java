package org.geeksforgeeks.digitalwallet.payment.bank;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

/** Sent to POST /api/bank/transfer — moves money from a wallet out to a bank account. */
@Value
@Builder
@Jacksonized
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BankTransferRequest {
    String walletId;
    BigDecimal amount;
    String bankAccountNumber;
    String ifscCode;
    String accountHolderName;
    TransferMode mode;
    String remarks;
}
