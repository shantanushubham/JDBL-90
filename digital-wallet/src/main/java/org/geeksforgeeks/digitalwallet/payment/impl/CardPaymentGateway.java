package org.geeksforgeeks.digitalwallet.payment.impl;

import org.geeksforgeeks.digitalwallet.payment.PaymentGateway;
import org.geeksforgeeks.digitalwallet.payment.PaymentResult;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class CardPaymentGateway implements PaymentGateway {

    @Override
    public PaymentResult processPayment(BigDecimal amount) {
        // TODO: Integrate with actual card payment provider (e.g. Stripe, Razorpay)
        String referenceId = "CARD-" + UUID.randomUUID();
        return new PaymentResult(true, referenceId, "Card payment of ₹" + amount + " processed successfully");
    }
}
