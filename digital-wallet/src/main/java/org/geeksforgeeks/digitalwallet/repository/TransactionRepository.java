package org.geeksforgeeks.digitalwallet.repository;

import org.geeksforgeeks.digitalwallet.domain.entity.Transaction;
import org.geeksforgeeks.digitalwallet.domain.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.senderWallet = :wallet AND t.type = 'TRANSFER_TO_BANK' AND t.status = 'SUCCESS' AND t.createdAt >= :since")
    BigDecimal getDailyBankTransferSum(@Param("wallet") Wallet wallet, @Param("since") LocalDateTime since);
}
