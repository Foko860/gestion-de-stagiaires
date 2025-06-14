package com.laosarl.gestion_de_stagiaires.security.utils;

import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentUserUtils {
    public static String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
