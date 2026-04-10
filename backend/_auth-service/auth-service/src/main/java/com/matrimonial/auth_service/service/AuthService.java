package com.matrimonial.auth_service.service;

import com.matrimonial.auth_service.dto.*;
import com.matrimonial.auth_service.entity.OtpVerification;
import com.matrimonial.auth_service.entity.RefreshToken;
import com.matrimonial.auth_service.entity.User;
import com.matrimonial.auth_service.entity.enums.OtpType;
import com.matrimonial.auth_service.entity.enums.Role;
import com.matrimonial.auth_service.repository.OtpVerificationRepository;
import com.matrimonial.auth_service.repository.RefreshTokenRepository;
import com.matrimonial.auth_service.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.Random;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final OtpVerificationRepository otpVerificationRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    // REGISTER METHOD
    @Transactional
    public String register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .gender(request.getGender())
                .role(Role.USER)
                .registeredBy(request.getRegisteredBy())
                .build();

        User savedUser = userRepository.save(user);

        String otp = generateOtp();
        OtpVerification otpVerification = OtpVerification.builder()
                .user(savedUser)
                .otpCode(otp)
                .otpType(OtpType.EMAIL_VERIFY)
                .expiresAt(LocalDateTime.now().plusMinutes(10))
                .build();

        otpVerificationRepository.save(otpVerification);

        // TODO: Kafka event publish
        // Notification Service email
        // Notification Service

        return "Registration successful. Please verify your email with OTP sent to " + request.getEmail();
    }

    // LOGIN METHOD
    @Transactional
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getIsActive()) {
            throw new RuntimeException("Account is blocked. Contact support.");
        }

        if (!user.getIsEmailVerified()) {
            throw new RuntimeException("Please verify your email first");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        RefreshToken refreshTokenEntity = RefreshToken.builder()
                .token(refreshToken)
                .user(user)
                .expiresAt(LocalDateTime.now().plusDays(7))
                .build();
        refreshTokenRepository.save(refreshTokenEntity);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .email(user.getEmail())
                .role(user.getRole().name())
                .gender(user.getGender().name())
                .message("Login successful")
                .build();
    }

    // LOGOUT METHOD
    @Transactional
    public String logout(String refreshToken) {

        RefreshToken token = refreshTokenRepository
                .findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        token.setIsRevoked(true);
        refreshTokenRepository.save(token);

        return "Logged out successfully";
    }

    @Transactional
    public String verifyOtp(@Valid OtpVerifyRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        OtpVerification otp = otpVerificationRepository
                .findByUserAndOtpTypeAndIsUsedFalse(user, OtpType.EMAIL_VERIFY)
                .orElseThrow(() -> new RuntimeException("OTP not found"));

        if (otp.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired. Please request new OTP.");
        }

        if (!otp.getOtpCode().equals(request.getOtpCode())) {
            throw new RuntimeException("Invalid OTP");
        }

        otp.setIsUsed(true);
        otpVerificationRepository.save(otp);
        user.setIsEmailVerified(true);
        userRepository.save(user);

        return "Email verified successfully. You can now login.";
    }

    // FORGOT PASSWORD METHOD
    @Transactional
    public String forgotPassword(@Valid ForgotPasswordRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String otp = generateOtp();
        OtpVerification otpVerification = OtpVerification.builder()
                .user(user)
                .otpCode(otp)
                .otpType(OtpType.PASSWORD_RESET)
                .expiresAt(LocalDateTime.now().plusMinutes(10))
                .build();

        otpVerificationRepository.save(otpVerification);

        // TODO: Kafka event publish करायचं आहे
        // Notification Service reset email

        return "Password reset OTP sent to " + request.getEmail();
    }
    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
}
