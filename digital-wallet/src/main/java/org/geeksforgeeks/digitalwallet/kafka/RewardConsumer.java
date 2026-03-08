package org.geeksforgeeks.digitalwallet.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.geeksforgeeks.digitalwallet.domain.entity.Wallet;
import org.geeksforgeeks.digitalwallet.domain.entity.Transaction;
import org.geeksforgeeks.digitalwallet.domain.enums.TransactionStatus;
import org.geeksforgeeks.digitalwallet.domain.enums.TransactionType;
import org.geeksforgeeks.digitalwallet.repository.TransactionRepository;
import org.geeksforgeeks.digitalwallet.repository.WalletRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class RewardConsumer {

    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    @KafkaListener(topics = "${app.kafka.reward-topic}", groupId = "${spring.kafka.consumer.group-id}")
    @Transactional
    public void consumeRewardEvent(RewardEvent event) {
        log.info("Received reward event for sender={}, amount={}",
                event.getSenderId(), event.getRewardAmount());

        Wallet wallet = this.walletRepository.findByUserId(event.getSenderId())
                .orElseThrow(() -> new RuntimeException("Wallet not found for user: " + event.getSenderId()));

        wallet.setBalance(wallet.getBalance().add(event.getRewardAmount()));
        this.walletRepository.save(wallet);

        Transaction rewardTransaction = Transaction.builder()
                .receiverWallet(wallet)
                .amount(event.getRewardAmount())
                .type(TransactionType.ADD_MONEY)
                .status(TransactionStatus.SUCCESS)
                .description("Reward credited for transaction [Ref: " + event.getTransactionId() + "]")
                .build();

        this.transactionRepository.save(rewardTransaction);
        log.info("Reward of ₹{} credited to wallet={}", event.getRewardAmount(), wallet.getId());
    }
}
