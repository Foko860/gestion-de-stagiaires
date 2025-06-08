package com.laosarl.gestion_de_stagiaires.security.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    CURRENT_USER_NOT_FOUND("Something wrong a current user is not found");

    private final String value;
}
