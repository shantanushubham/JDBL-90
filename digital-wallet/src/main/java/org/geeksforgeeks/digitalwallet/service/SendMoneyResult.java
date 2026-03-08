package org.geeksforgeeks.digitalwallet.service;

import lombok.Builder;
import lombok.Getter;
import org.geeksforgeeks.digitalwallet.domain.entity.Transaction;

import java.math.BigDecimal;

@Getter
@Builder
public class SendMoneyResult {
    private final Transaction transaction;
    private final BigDecimal rewardAmount; // null → no reward this time
}
