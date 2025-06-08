package com.laosarl.gestion_de_stagiaires.security.api;

import com.laosarl.gestion_de_stagiaires.security.domain.user.CurrentUserNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ResourcesSecurityAdvice {
    @ExceptionHandler({CurrentUserNotFound.class})
    public ResponseEntity<String> handleCurrentUserNotFound(CurrentUserNotFound exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }
}
