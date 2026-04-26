package com.matrimonial.profile.controller;

import com.matrimonial.profile.dtoes.CreateProfileRequest;
import com.matrimonial.profile.dtoes.response.ProfileResponse;
import com.matrimonial.profile.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping
    public ResponseEntity<ProfileResponse> createProfile(@Valid @RequestBody CreateProfileRequest request){
        ProfileResponse response = profileService.createProfile(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ProfileResponse> getProfileByUserId(@PathVariable Long userId){
        ProfileResponse response = profileService.getProfileByUserId(userId);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{profileId}")
    public ResponseEntity<ProfileResponse> getProfileById(
            @PathVariable Long profileId) {
        ProfileResponse response = profileService.getProfileById(profileId);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/search")
    public ResponseEntity<List<ProfileResponse>> searchProfiles(
            @RequestParam String gender,
            @RequestParam(required = false) String religion,
            @RequestParam(required = false) String city) {
        List<ProfileResponse> profiles =
                profileService.searchProfiles(gender, religion, city);
        return ResponseEntity.ok(profiles);
    }


}
