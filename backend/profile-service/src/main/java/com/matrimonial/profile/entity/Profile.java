package com.matrimonial.profile.entity;

import com.matrimonial.profile.entity.enums.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "profiles")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", unique = true, nullable = false)
    private Long userId;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name="date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender" , nullable = false)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "marital_status", nullable = false)
    private MaritalStatus maritalStatus;

    @Column(name = "height_cm")
    private Integer heightCm;

    @Column(name = "weight_kg")
    private Integer weightKg;

    @Enumerated(EnumType.STRING)
    @Column(name = "body_type")
    private BodyType bodyType;

    @Enumerated(EnumType.STRING)
    @Column(name = "complexion")
    private Cmplexion complexion;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "state", length = 100)
    private String state;

    @Column(name = "country", length = 100)
    private String country;

    @Builder.Default
    @Column(name = "is_nri")
    private Boolean isNri = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "religion")
    private Religion religion;

    @Column(name = "caste", length = 100)
    private String caste;

    @Column(name = "sub_caste", length = 100)
    private String subCaste;

    @Column(name = "mother_tongue", length = 50)
    private String motherTongue;

    @Column(name = "rashi", length = 50)
    private String rashi;

    @Column(name = "nakshatra", length = 50)
    private String nakshatra;

    @Column(name = "gotra", length = 50)
    private String gotra;

    @Builder.Default
    @Column(name = "is_manglik")
    private Boolean isManglik = false;

    @Column(name = "birth_time", length = 20)
    private String birthTime;

    @Column(name = "birth_place", length = 100)
    private String birthPlace;

    @Enumerated(EnumType.STRING)
    @Column(name = "education_level")
    private EducationLevel educationLevel;

    @Column(name = "education_detail", length = 200)
    private String educationDetail;

    @Column(name = "college_name", length = 200)
    private String collegeName;

    @Enumerated(EnumType.STRING)
    @Column(name = "employment_type")
    private EmploymentType employmentType;

    @Column(name = "occupation", length = 100)
    private String occupation;

    @Column(name = "company_name", length = 200)
    private String companyName;

    @Column(name = "annual_income", length = 50)
    private String annualIncome;

    @Column(name = "family_type", length = 50)
    private String familyType;

    @Column(name = "family_status", length = 50)
    private String familyStatus;

    @Column(name = "father_occupation", length = 100)
    private String fatherOccupation;

    @Column(name = "mother_occupation", length = 100)
    private String motherOccupation;

    @Column(name = "siblings_count")
    private Integer siblingsCount;

    @Column(name = "about_me", columnDefinition = "TEXT")
    private String aboutMe;

    // Partner preferences
    @Column(name = "partner_age_min")
    private Integer partnerAgeMin;

    @Column(name = "partner_age_max")
    private Integer partnerAgeMax;

    @Column(name = "partner_religion", length = 100)
    private String partnerReligion;

    @Column(name = "partner_caste", length = 100)
    private String partnerCaste;

    @Column(name = "partner_education", length = 100)
    private String partnerEducation;

    @Column(name = "partner_location", length = 200)
    private String partnerLocation;

    @Builder.Default
    @Column(name = "is_verified")
    private Boolean isVerified = false;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "profile_status")
    private ProfileStatus profileStatus = ProfileStatus.INCOMPLETE;

    @OneToMany(
            mappedBy = "profile",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<ProfilePhoto> photos;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void beforeSave() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void beforeUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}
