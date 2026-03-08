package org.geeksforgeeks.digitalwallet.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class TransferToBankRequest {
    private BigDecimal amount;
    private String bankAccountNumber;
    private String ifscCode;
}
