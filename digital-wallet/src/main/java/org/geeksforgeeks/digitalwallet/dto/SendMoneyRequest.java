package org.geeksforgeeks.digitalwallet.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class SendMoneyRequest {
    private String receiverPhoneNo;
    private BigDecimal amount;
}
