package com.laosarl.gestion_de_stagiaires.security.utils;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record CreateUserData (
        String email,
        String password,
        String firstname,
        String lastname,
        LocalDate dateOfBird,
        String gender,
        String countryCode,
        String number,
        UUID profileImageId
) {
}
