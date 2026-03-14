package org.geeksforgeeks.digitalwallet.payment.bank;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

/** Sent to POST /api/bank/credit — pulls money from a bank account into a wallet. */
@Value
@Builder
@Jacksonized
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BankCreditRequest {
    String walletId;
    BigDecimal amount;
    String bankAccountNumber;
    String ifscCode;
    TransferMode mode;
    String remarks;
}
