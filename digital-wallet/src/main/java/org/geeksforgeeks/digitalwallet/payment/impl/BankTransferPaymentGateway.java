package org.geeksforgeeks.digitalwallet.payment.impl;

import lombok.extern.slf4j.Slf4j;
import org.geeksforgeeks.digitalwallet.payment.PaymentGateway;
import org.geeksforgeeks.digitalwallet.payment.PaymentResult;
import org.geeksforgeeks.digitalwallet.payment.bank.BankCreditRequest;
import org.geeksforgeeks.digitalwallet.payment.bank.BankCreditResponse;
import org.geeksforgeeks.digitalwallet.payment.bank.BankErrorResponse;
import org.geeksforgeeks.digitalwallet.payment.bank.TransferMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Component
public class BankTransferPaymentGateway implements PaymentGateway {

    private final WebClient webClient;

    public BankTransferPaymentGateway(
            WebClient.Builder webClientBuilder,
            @Value("${app.bank.service-url:http://localhost:8085}") String bankServiceUrl) {
        this.webClient = webClientBuilder.baseUrl(bankServiceUrl).build();
    }

    @Override
    public PaymentResult processPayment(BigDecimal amount) {
        BankCreditRequest request = BankCreditRequest.builder()
                .walletId(UUID.randomUUID().toString())
                .amount(amount)
                .bankAccountNumber("000000000000")
                .ifscCode("SBIN0001234")
                .mode(TransferMode.IMPS)
                .remarks("Add money via bank transfer")
                .build();

        try {
            log.info("op=processPayment class=BankTransferPaymentGateway threadName={}", Thread.currentThread().getName());
            return this.webClient.post()
                    .uri("/api/bank/credit")
                    .bodyValue(request)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, clientResponse ->
                            clientResponse.bodyToMono(BankErrorResponse.class)
                                    .flatMap(err -> {
                                        log.warn("Bank credit rejected: status={}, code={}, error={}",
                                                clientResponse.statusCode(), err.getCode(), err.getError());
                                        return Mono.error(new BankServiceException(err.getError(), err.getCode()));
                                    })
                    )
                    .bodyToMono(BankCreditResponse.class)
                    .map(response -> {
                        log.info("Bank credit succeeded: referenceId={}, amount={}",
                                response.getReferenceId(), amount);
                        return new PaymentResult(true, response.getReferenceId(), response.getMessage());
                    })
                    .onErrorResume(BankServiceException.class, ex ->
                            Mono.just(new PaymentResult(false, null, ex.getMessage()))
                    )
                    .onErrorResume(Exception.class, ex -> {
                        log.error("Failed to reach bank service: {}", ex.getMessage());
                        return Mono.just(new PaymentResult(false, null,
                                "Bank service unavailable — please try again later"));
                    })
                    .block();

        } catch (Exception ex) {
            log.error("Unexpected error during bank transfer: {}", ex.getMessage());
            return new PaymentResult(false, null, "Bank service unavailable — please try again later");
        }
    }

    private static class BankServiceException extends RuntimeException {
        private final String code;

        BankServiceException(String message, String code) {
            super(message);
            this.code = code;
        }

        String getCode() {
            return code;
        }
    }
}
