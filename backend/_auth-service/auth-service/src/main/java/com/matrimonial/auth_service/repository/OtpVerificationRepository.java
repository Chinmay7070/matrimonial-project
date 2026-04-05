package com.matrimonial.auth_service.repository;

import com.matrimonial.auth_service.entity.OtpVerification;
import com.matrimonial.auth_service.entity.User;
import com.matrimonial.auth_service.entity.enums.OtpType;

import java.util.Optional;

public interface OtpVerificationRepository {

    Optional<OtpVerification> findByUserAndOtpTypeAndIsUsedFalse(
            User user,
            OtpType otpType
    );
}
