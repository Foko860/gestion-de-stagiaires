package com.laosarl.gestion_de_stagiaires.domain.user;


import com.laosarl.gestion_de_stagiaires.security.utils.ErrorCode;

public class CurrentUserNotFound extends Throwable{
    public CurrentUserNotFound(ErrorCode errorCode) {
        super(errorCode.getValue());
    }
}
