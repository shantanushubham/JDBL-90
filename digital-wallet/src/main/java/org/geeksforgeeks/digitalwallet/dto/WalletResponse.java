package org.geeksforgeeks.digitalwallet.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class WalletResponse {
    private UUID walletId;
    private BigDecimal balance;
}
