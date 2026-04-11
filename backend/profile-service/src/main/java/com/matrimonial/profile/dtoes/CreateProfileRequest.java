package com.matrimonial.profile.dtoes;


import com.matrimonial.profile.entity.enums.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class CreateProfileRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @NotNull(message = "Marital status is required")
    private MaritalStatus maritalStatus;

    private Integer heightCm;
    private Integer weightKg;
    private BodyType bodyType;
    private Cmplexion complexion;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "Country is required")
    private String country;

    private Boolean isNri = false;

    private Religion religion;
    private String caste;
    private String subCaste;
    private String motherTongue;

    // Kundli details
    private String rashi;
    private String nakshatra;
    private String gotra;
    private Boolean isManglik = false;
    private String birthTime;
    private String birthPlace;

    // Education
    private EducationLevel educationLevel;
    private String educationDetail;
    private String collegeName;

    // Career
    private EmploymentType employmentType;
    private String occupation;
    private String companyName;
    private String annualIncome;

    // Family
    private String familyType;
    private String familyStatus;
    private String fatherOccupation;
    private String motherOccupation;
    private Integer siblingsCount;

    // About
    private String aboutMe;

    // Partner preferences
    private Integer partnerAgeMin;
    private Integer partnerAgeMax;
    private String partnerReligion;
    private String partnerCaste;
    private String partnerEducation;
    private String partnerLocation;
}
