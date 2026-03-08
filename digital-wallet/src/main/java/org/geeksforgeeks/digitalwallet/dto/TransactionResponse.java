package org.geeksforgeeks.digitalwallet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.geeksforgeeks.digitalwallet.domain.enums.TransactionStatus;
import org.geeksforgeeks.digitalwallet.domain.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {
    private UUID transactionId;
    private TransactionStatus status;
    private TransactionType type;
    private BigDecimal amount;
    private String description;
    private LocalDateTime timestamp;
    @Builder.Default
    private String rewardNote = "Better Luck Next Time"; // non-null when a reward is pending async credit
}
