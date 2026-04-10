package com.matrimonial.auth_service.repository;

import com.matrimonial.auth_service.entity.OtpVerification;
import com.matrimonial.auth_service.entity.User;
import com.matrimonial.auth_service.entity.enums.OtpType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface OtpVerificationRepository
        extends JpaRepository<OtpVerification, Long> {

    Optional<OtpVerification> findByUserAndOtpTypeAndIsUsedFalse(
            User user,
            OtpType otpType
    );
}
