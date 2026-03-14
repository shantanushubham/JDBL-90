package org.geeksforgeeks.digitalwallet.payment.bank;

import lombok.Data;
import lombok.NoArgsConstructor;

/** Error response returned by the bank service on 4xx / 5xx. */
@Data
@NoArgsConstructor
public class BankErrorResponse {
    private boolean success;
    private String error;
    private String code;
    private String timestamp;
}
