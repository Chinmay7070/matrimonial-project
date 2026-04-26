package com.matrimonial.profile.repository;

import com.matrimonial.profile.entity.Profile;
import com.matrimonial.profile.entity.ProfilePhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface ProfilePhotoRepository extends JpaRepository<ProfilePhoto, Long>{

    List<ProfilePhoto> findByProfileId(Long profileId);

    Optional<ProfilePhoto> findByProfileIdAndIsPrimaryTrue(Long profileId);
}
