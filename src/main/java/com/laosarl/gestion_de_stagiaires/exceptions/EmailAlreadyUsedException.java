package com.laosarl.gestion_de_stagiaires.exceptions;

import lombok.Getter;

@Getter
public class EmailAlreadyUsedException extends RuntimeException {
    public EmailAlreadyUsedException(String message) {
        super(message);
    }
}
