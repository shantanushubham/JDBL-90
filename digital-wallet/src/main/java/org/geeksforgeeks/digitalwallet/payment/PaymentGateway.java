package org.geeksforgeeks.digitalwallet.payment;

import java.math.BigDecimal;

/**
 * Abstraction for all payment gateways used to add money to the wallet.
 * Each payment method (Card, UPI, Bank Transfer) implements this interface.
 */
public interface PaymentGateway {

    /**
     * Processes the incoming payment for the given amount.
     *
     * @param amount the amount to be loaded into the wallet
     * @return a PaymentResult indicating success/failure and a reference ID
     */
    PaymentResult processPayment(BigDecimal amount);
}
