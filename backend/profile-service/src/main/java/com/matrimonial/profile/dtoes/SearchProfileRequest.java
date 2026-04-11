package com.matrimonial.profile.dtoes;

import com.matrimonial.profile.entity.enums.Gender;
import com.matrimonial.profile.entity.enums.Religion;

public class SearchProfileRequest {
    private Gender gender;

    private Religion religion;
    private String city;
    private Integer ageMin;
    private Integer ageMax;
    private String annualIncome;
}
