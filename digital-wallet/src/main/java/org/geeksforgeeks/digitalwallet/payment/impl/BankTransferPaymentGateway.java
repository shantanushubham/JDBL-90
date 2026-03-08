package org.geeksforgeeks.digitalwallet.payment.impl;

import org.geeksforgeeks.digitalwallet.payment.PaymentGateway;
import org.geeksforgeeks.digitalwallet.payment.PaymentResult;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class BankTransferPaymentGateway implements PaymentGateway {

    @Override
    public PaymentResult processPayment(BigDecimal amount) {
        // TODO: Integrate with actual NEFT/IMPS/RTGS bank transfer provider
        String referenceId = "BANK-" + UUID.randomUUID();
        return new PaymentResult(true, referenceId, "Bank transfer of ₹" + amount + " processed successfully");
    }
}
