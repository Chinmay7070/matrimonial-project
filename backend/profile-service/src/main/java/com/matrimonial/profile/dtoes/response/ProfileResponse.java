package com.matrimonial.profile.dtoes.response;

import com.matrimonial.profile.entity.enums.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {

    private Long id;
    private Long userId;
    private String fullName;
    private LocalDate dateOfBirth;
    private Gender gender;
    private MaritalStatus maritalStatus;
    private Integer heightCm;
    private Integer weightKg;
    private BodyType bodyType;
    private Cmplexion complexion;
    private String city;
    private String state;
    private String country;
    private Boolean isNri;
    private Religion religion;
    private String caste;
    private String subCaste;
    private String motherTongue;

    // Kundli
    private String rashi;
    private String nakshatra;
    private String gotra;
    private Boolean isManglik;
    private String birthTime;
    private String birthPlace;

    // Education & Career
    private EducationLevel educationLevel;
    private String educationDetail;
    private String collegeName;
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

    // Status
    private Boolean isVerified;
    private ProfileStatus profileStatus;

    // Photos
    private List<String> photoUrls;
    private String primaryPhotoUrl;
}


