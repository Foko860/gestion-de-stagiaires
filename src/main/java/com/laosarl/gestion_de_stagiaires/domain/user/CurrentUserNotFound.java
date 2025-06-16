package com.laosarl.gestion_de_stagiaires.domain.user;


public class CurrentUserNotFound extends RuntimeException{
    public CurrentUserNotFound(String message) {
        super(message);
    }
}
