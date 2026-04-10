package com.matrimonial.auth_service.dto;

import com.matrimonial.auth_service.entity.enums.Gender;
import com.matrimonial.auth_service.entity.enums.RegisteredBy;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email Format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8 , message = "Password must be at least 8 chara")
    private String password;

    @NotBlank(message = "Phone is required")
    private String phone;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @NotNull(message = "Registered by is required")
    private RegisteredBy registeredBy;
}
