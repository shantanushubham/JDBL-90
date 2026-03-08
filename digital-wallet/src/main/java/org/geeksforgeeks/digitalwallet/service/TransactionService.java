package org.geeksforgeeks.digitalwallet.service;

import lombok.RequiredArgsConstructor;
import org.geeksforgeeks.digitalwallet.domain.entity.Transaction;
import org.geeksforgeeks.digitalwallet.domain.entity.User;
import org.geeksforgeeks.digitalwallet.domain.entity.Wallet;
import org.geeksforgeeks.digitalwallet.domain.enums.TransactionStatus;
import org.geeksforgeeks.digitalwallet.domain.enums.TransactionType;
import org.geeksforgeeks.digitalwallet.dto.AddMoneyRequest;
import org.geeksforgeeks.digitalwallet.dto.SendMoneyRequest;
import org.geeksforgeeks.digitalwallet.dto.TransferToBankRequest;
import org.geeksforgeeks.digitalwallet.repository.TransactionRepository;
import org.geeksforgeeks.digitalwallet.repository.UserRepository;
import org.geeksforgeeks.digitalwallet.repository.WalletRepository;
import org.geeksforgeeks.digitalwallet.domain.enums.UserType;
import org.geeksforgeeks.digitalwallet.kafka.RewardEvent;
import org.geeksforgeeks.digitalwallet.kafka.RewardProducer;
import org.geeksforgeeks.digitalwallet.payment.PaymentGateway;
import org.geeksforgeeks.digitalwallet.payment.PaymentGatewayFactory;
import org.geeksforgeeks.digitalwallet.payment.PaymentResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final PaymentGatewayFactory paymentGatewayFactory;
    private final RewardProducer rewardProducer;

    private static final SecureRandom RANDOM = new SecureRandom();

    @Transactional
    public Transaction addMoney(User user, AddMoneyRequest request) {
        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        // Delegate payment processing to the appropriate gateway
        PaymentGateway gateway = this.paymentGatewayFactory.getGateway(request.getPaymentMethod());
        PaymentResult result = gateway.processPayment(request.getAmount());

        if (!result.isSuccess()) {
            throw new RuntimeException("Payment failed: " + result.getMessage());
        }

        Wallet wallet = this.walletRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Wallet not found for the user"));

        wallet.setBalance(wallet.getBalance().add(request.getAmount()));
        this.walletRepository.save(wallet);

        Transaction transaction = Transaction.builder()
                .receiverWallet(wallet)
                .amount(request.getAmount())
                .type(TransactionType.ADD_MONEY)
                .status(TransactionStatus.SUCCESS)
                .description(result.getMessage() + " [Ref: " + result.getReferenceId() + "]")
                .build();

        return this.transactionRepository.save(transaction);
    }

    @Transactional
    public SendMoneyResult sendMoney(User sender, SendMoneyRequest request) {
        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        Wallet senderWallet = this.walletRepository.findByUserId(sender.getId())
                .orElseThrow(() -> new RuntimeException("Sender wallet not found"));

        if (senderWallet.getBalance().compareTo(request.getAmount()) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        User receiver = this.userRepository.findByPhoneNo(request.getReceiverPhoneNo())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        if (sender.getId().equals(receiver.getId())) {
            throw new IllegalArgumentException("Cannot send money to yourself");
        }

        Wallet receiverWallet = this.walletRepository.findByUserId(receiver.getId())
                .orElseThrow(() -> new RuntimeException("Receiver wallet not found"));

        // Deduct from sender
        senderWallet.setBalance(senderWallet.getBalance().subtract(request.getAmount()));

        // Add to receiver
        receiverWallet.setBalance(receiverWallet.getBalance().add(request.getAmount()));

        this.walletRepository.saveAll(List.of(senderWallet, receiverWallet));

        Transaction transaction = Transaction.builder()
                .senderWallet(senderWallet)
                .receiverWallet(receiverWallet)
                .amount(request.getAmount())
                .type(TransactionType.SEND_MONEY)
                .status(TransactionStatus.SUCCESS)
                .description("Transferred to " + receiver.getFirstName())
                .build();

        Transaction saved = this.transactionRepository.save(transaction);

        // Reward logic: 10% chance of getting ₹0.01 – ₹10
        BigDecimal rewardAmount = this.calculateReward();
        if (rewardAmount.compareTo(BigDecimal.ZERO) > 0) {
            RewardEvent event = RewardEvent.builder()
                    .senderId(sender.getId())
                    .senderWalletId(senderWallet.getId())
                    .rewardAmount(rewardAmount)
                    .transactionId(saved.getId())
                    .build();
            this.rewardProducer.publishRewardEvent(event);
        }

        return SendMoneyResult.builder()
                .transaction(saved)
                .rewardAmount(rewardAmount.compareTo(BigDecimal.ZERO) > 0 ? rewardAmount : null)
                .build();
    }

    /**
     * 90% of the time returns 0. Otherwise returns a random amount between ₹0.01 and ₹10.
     */
    private BigDecimal calculateReward() {
        if (RANDOM.nextDouble() >= 0.10) {
            return BigDecimal.ZERO;
        }
        // Random value in [0.01, 10.00]
        double raw = 0.01 + (RANDOM.nextDouble() * 9.99);
        return BigDecimal.valueOf(raw).setScale(2, RoundingMode.HALF_UP);
    }

    @Transactional
    public Transaction transferToBank(User user, TransferToBankRequest request) {
        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        Wallet wallet = this.walletRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        // RBAC: REGULAR users are capped at 20k per 24h window for bank transfers
        if (user.getUserType() == UserType.REGULAR) {
            BigDecimal dailySum = this.transactionRepository.getDailyBankTransferSum(wallet,
                    LocalDateTime.now().minusHours(24));
            if (dailySum.add(request.getAmount()).compareTo(new BigDecimal("20000")) > 0) {
                throw new IllegalArgumentException(
                        "Regular users cannot transfer more than ₹20,000 to a bank account within 24 hours");
            }
        }

        // Commission: 1% for REGULAR, 0.75% for PRO
        BigDecimal commissionRate = user.getUserType() == UserType.PRO
                ? new BigDecimal("0.0075")
                : new BigDecimal("0.01");

        BigDecimal commission = request.getAmount().multiply(commissionRate);
        BigDecimal totalDeduction = request.getAmount().add(commission);

        if (wallet.getBalance().compareTo(totalDeduction) < 0) {
            throw new IllegalArgumentException("Insufficient balance (amount + commission of " + commission + ")");
        }

        // Deduct amount + commission from wallet
        wallet.setBalance(wallet.getBalance().subtract(totalDeduction));
        this.walletRepository.save(wallet);

        // TODO: Add money to bank account by hitting bank Service

        Transaction transaction = Transaction.builder()
                .senderWallet(wallet)
                .amount(request.getAmount())
                .type(TransactionType.TRANSFER_TO_BANK)
                .status(TransactionStatus.SUCCESS)
                .description("Transfer to bank account " + request.getBankAccountNumber())
                .build();

        return this.transactionRepository.save(transaction);
    }
}
