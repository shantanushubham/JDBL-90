package org.geeksforgeeks.digitalwallet.service;

import lombok.RequiredArgsConstructor;
import org.geeksforgeeks.digitalwallet.domain.entity.User;
import org.geeksforgeeks.digitalwallet.dto.LoginRequest;
import org.geeksforgeeks.digitalwallet.dto.RegisterRequest;
import org.geeksforgeeks.digitalwallet.repository.UserRepository;
import org.geeksforgeeks.digitalwallet.security.JwtUtil;
import org.geeksforgeeks.digitalwallet.domain.entity.Wallet;
import org.geeksforgeeks.digitalwallet.repository.WalletRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public String registerUser(RegisterRequest request) {
        if (this.userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNo(request.getPhoneNo())
                .email(request.getEmail())
                .password(this.passwordEncoder.encode(request.getPassword()))
                .dob(request.getDob())
                .pan(request.getPan())
                .build();

        user = this.userRepository.save(user);

        Wallet wallet = Wallet.builder()
                .user(user)
                .build();
        this.walletRepository.save(wallet);

        return this.jwtUtil.generateToken(user);
    }

    public String loginUser(LoginRequest request) {
        this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        User user = this.userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return this.jwtUtil.generateToken(user);
    }
}
