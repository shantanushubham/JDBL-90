package org.geeksforgeeks.digitalwallet.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RewardEvent {
    private UUID senderId;
    private UUID senderWalletId;
    private BigDecimal rewardAmount;
    private UUID transactionId;
}
