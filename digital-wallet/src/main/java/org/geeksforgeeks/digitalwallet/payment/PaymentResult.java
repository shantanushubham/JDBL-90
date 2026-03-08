package org.geeksforgeeks.digitalwallet.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentResult {
    private final boolean success;
    private final String referenceId;
    private final String message;
}
