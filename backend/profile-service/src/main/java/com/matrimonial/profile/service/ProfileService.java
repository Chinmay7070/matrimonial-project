package com.matrimonial.profile.service;

import com.matrimonial.profile.dtoes.CreateProfileRequest;
import com.matrimonial.profile.dtoes.response.ProfileResponse;
import com.matrimonial.profile.entity.Profile;
import com.matrimonial.profile.entity.enums.ProfileStatus;
import com.matrimonial.profile.repository.ProfilePhotoRepository;
import com.matrimonial.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfilePhotoRepository profilePhotoRepository;

    public ProfileResponse createProfile(CreateProfileRequest request) {

        if (profileRepository.existsByUserId((request.getUserId()))) {
            throw new RuntimeException("Profile already exists for this user");
        }

        Profile profile = Profile.builder()
                .userId((request.getUserId()))
                .fullName(request.getFullName())
                .dateOfBirth(request.getDateOfBirth())
                .gender(request.getGender())
                .maritalStatus(request.getMaritalStatus())
                .heightCm(request.getHeightCm())
                .weightKg(request.getWeightKg())
                .bodyType(request.getBodyType())
                .complexion(request.getComplexion())
                .city(request.getCity())
                .state(request.getState())
                .country(request.getCountry())
                .isNri(request.getIsNri())
                .religion(request.getReligion())
                .caste(request.getCaste())
                .subCaste(request.getSubCaste())
                .motherTongue(request.getMotherTongue())
                .rashi(request.getRashi())
                .nakshatra(request.getNakshatra())
                .gotra(request.getGotra())
                .isManglik(request.getIsManglik())
                .birthTime(request.getBirthTime())
                .birthPlace(request.getBirthPlace())
                .educationLevel(request.getEducationLevel())
                .educationDetail(request.getEducationDetail())
                .collegeName(request.getCollegeName())
                .employmentType(request.getEmploymentType())
                .occupation(request.getOccupation())
                .companyName(request.getCompanyName())
                .annualIncome(request.getAnnualIncome())
                .familyType(request.getFamilyType())
                .familyStatus(request.getFamilyStatus())
                .fatherOccupation(request.getFatherOccupation())
                .motherOccupation(request.getMotherOccupation())
                .siblingsCount(request.getSiblingsCount())
                .aboutMe(request.getAboutMe())
                .partnerAgeMin(request.getPartnerAgeMin())
                .partnerAgeMax(request.getPartnerAgeMax())
                .partnerReligion(request.getPartnerReligion())
                .partnerCaste(request.getPartnerCaste())
                .partnerEducation(request.getPartnerEducation())
                .partnerLocation(request.getPartnerLocation())
                .profileStatus(ProfileStatus.PENDING_REVIEW)
                .build();
        Profile savedProfile = profileRepository.save(profile);

        return mapToResponse(savedProfile);

    }
    public ProfileResponse getProfileById(Long profileId) {

        Profile profile = profileRepository
                .findById(profileId)
                .orElseThrow(() ->
                        new RuntimeException("Profile not found"));

        return mapToResponse(profile);
    }
    public ProfileResponse getProfileByUserId(Long userId) {

        Profile profile = profileRepository
                .findByUserId(userId)
                .orElseThrow(() ->
                        new RuntimeException("Profile not found"));

        return mapToResponse(profile);
    }
    public List<ProfileResponse> searchProfiles(
            String genderStr,
            String religionStr,
            String city) {

        com.matrimonial.profile.entity.enums.Gender gender =
                com.matrimonial.profile.entity.enums.Gender
                        .valueOf(genderStr.toUpperCase());

        com.matrimonial.profile.entity.enums.Religion religion = null;
        if (religionStr != null && !religionStr.isEmpty()) {
            religion = com.matrimonial.profile.entity.enums.Religion
                    .valueOf(religionStr.toUpperCase());
        }

        List<Profile> profiles = profileRepository
                .searchProfiles(gender, religion, city);

        return profiles.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }


    private ProfileResponse mapToResponse(Profile profile){
        List<String> photoUrls = profilePhotoRepository
                .findByProfileId(profile.getId())
                .stream()
                .filter(photo -> photo.getIsApproved())
                .map(photo -> photo.getPhotoUrl())
                .collect(Collectors.toList());

        String primaryPhotoUrl = profilePhotoRepository
                .findByProfileIdAndIsPrimaryTrue(profile.getId())
                .map(photo -> photo.getPhotoUrl())
                .orElse(null);

        return ProfileResponse.builder()
                .id(profile.getId())
                .userId(profile.getUserId())
                .fullName(profile.getFullName())
                .dateOfBirth(profile.getDateOfBirth())
                .gender(profile.getGender())
                .maritalStatus(profile.getMaritalStatus())
                .heightCm(profile.getHeightCm())
                .weightKg(profile.getWeightKg())
                .bodyType(profile.getBodyType())
                .complexion(profile.getComplexion())
                .city(profile.getCity())
                .state(profile.getState())
                .country(profile.getCountry())
                .isNri(profile.getIsNri())
                .religion(profile.getReligion())
                .caste(profile.getCaste())
                .subCaste(profile.getSubCaste())
                .motherTongue(profile.getMotherTongue())
                .rashi(profile.getRashi())
                .nakshatra(profile.getNakshatra())
                .gotra(profile.getGotra())
                .isManglik(profile.getIsManglik())
                .birthTime(profile.getBirthTime())
                .birthPlace(profile.getBirthPlace())
                .educationLevel(profile.getEducationLevel())
                .educationDetail(profile.getEducationDetail())
                .collegeName(profile.getCollegeName())
                .employmentType(profile.getEmploymentType())
                .occupation(profile.getOccupation())
                .companyName(profile.getCompanyName())
                .annualIncome(profile.getAnnualIncome())
                .familyType(profile.getFamilyType())
                .familyStatus(profile.getFamilyStatus())
                .fatherOccupation(profile.getFatherOccupation())
                .motherOccupation(profile.getMotherOccupation())
                .siblingsCount(profile.getSiblingsCount())
                .aboutMe(profile.getAboutMe())
                .partnerAgeMin(profile.getPartnerAgeMin())
                .partnerAgeMax(profile.getPartnerAgeMax())
                .partnerReligion(profile.getPartnerReligion())
                .partnerCaste(profile.getPartnerCaste())
                .partnerEducation(profile.getPartnerEducation())
                .partnerLocation(profile.getPartnerLocation())
                .isVerified(profile.getIsVerified())
                .profileStatus(profile.getProfileStatus())
                .photoUrls(photoUrls)
                .primaryPhotoUrl(primaryPhotoUrl)
                .build();
    }

}
