package com.matrimonial.profile.repository;

import com.matrimonial.profile.entity.Profile;
import com.matrimonial.profile.entity.enums.Gender;
import com.matrimonial.profile.entity.enums.ProfileStatus;
import com.matrimonial.profile.entity.enums.Religion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByUserId(Long userId);

    boolean existsByUserId(Long userId);

    @Query("SELECT p FROM Profile p WHERE " +
            "p.gender = :gender AND " +
            "p.profileStatus = 'ACTIVE' AND " +
            "(:religion IS NULL OR p.religion = :religion) AND " +
            "(:city IS NULL OR LOWER(p.city) LIKE LOWER(CONCAT('%', :city, '%')))")
    List<Profile> searchProfiles(
            @Param("gender") Gender gender,
            @Param("religion") Religion religion,
            @Param("city") String city
    );

    List<Profile> findByProfileStatus(ProfileStatus status); // this is for admin

}
