package org.geeksforgeeks.digitalwallet.payment.impl;

import org.geeksforgeeks.digitalwallet.payment.PaymentGateway;
import org.geeksforgeeks.digitalwallet.payment.PaymentResult;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class UpiPaymentGateway implements PaymentGateway {

    @Override
    public PaymentResult processPayment(BigDecimal amount) {
        // TODO: Integrate with actual UPI provider (e.g. Razorpay, PayU)
        String referenceId = "UPI-" + UUID.randomUUID();
        return new PaymentResult(true, referenceId, "UPI payment of ₹" + amount + " processed successfully");
    }
}
