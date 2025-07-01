package com.laosarl.gestion_de_stagiaires.utils;

import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentUser {
    public static String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
