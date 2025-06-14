package com.laosarl.gestion_de_stagiaires.exceptions;

import lombok.Getter;

@Getter
public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(String message) {
        super(message);
    }
}
