package org.geeksforgeeks.digitalwallet.payment;

import lombok.RequiredArgsConstructor;
import org.geeksforgeeks.digitalwallet.domain.enums.PaymentMethod;
import org.geeksforgeeks.digitalwallet.payment.impl.BankTransferPaymentGateway;
import org.geeksforgeeks.digitalwallet.payment.impl.CardPaymentGateway;
import org.geeksforgeeks.digitalwallet.payment.impl.UpiPaymentGateway;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentGatewayFactory {

    private final CardPaymentGateway cardPaymentGateway;
    private final UpiPaymentGateway upiPaymentGateway;
    private final BankTransferPaymentGateway bankTransferPaymentGateway;

    public PaymentGateway getGateway(PaymentMethod method) {
        return switch (method) {
            case CARD -> this.cardPaymentGateway;
            case UPI -> this.upiPaymentGateway;
            case BANK_TRANSFER -> this.bankTransferPaymentGateway;
        };
    }
}
