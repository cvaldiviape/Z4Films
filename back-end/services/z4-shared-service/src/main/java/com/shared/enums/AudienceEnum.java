package com.shared.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AudienceEnum {
    GENERAL_AUDIENCE("General Audience", "All ages"),
    ADULT_AUDIENCE("Adult Audience", "18+ years old");

    private final String displayName;
    private final String ageRange;

}