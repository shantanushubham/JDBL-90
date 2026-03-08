package org.geeksforgeeks.digitalwallet.dto;

import lombok.Data;
import org.geeksforgeeks.digitalwallet.domain.enums.PaymentMethod;

import java.math.BigDecimal;

@Data
public class AddMoneyRequest {
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
}
