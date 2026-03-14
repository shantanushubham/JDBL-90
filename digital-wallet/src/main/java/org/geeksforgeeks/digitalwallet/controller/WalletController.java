package org.geeksforgeeks.digitalwallet.controller;

import lombok.RequiredArgsConstructor;
import org.geeksforgeeks.digitalwallet.domain.entity.User;
import org.geeksforgeeks.digitalwallet.domain.entity.Wallet;
import org.geeksforgeeks.digitalwallet.dto.WalletResponse;
import org.geeksforgeeks.digitalwallet.repository.WalletRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletRepository walletRepository;

    @GetMapping
    public ResponseEntity<WalletResponse> getWalletDetails(@AuthenticationPrincipal User user) {
        Wallet wallet = this.walletRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        WalletResponse response = WalletResponse.builder()
                .walletId(wallet.getId())
                .balance(wallet.getBalance())
                .build();

        return ResponseEntity.ok(response);
    }
}