package com.laosarl.gestion_de_stagiaires.Controller;

import com.laosarl.gestion_de_stagiaires.domain.user.CurrentUserNotFound;
import com.laosarl.gestion_de_stagiaires.exceptions.EmailAlreadyUsedException;
import com.laosarl.gestion_de_stagiaires.exceptions.StudentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler({EmailAlreadyUsedException.class})
    public ResponseEntity<String> handleEmailAlreadyUsedException(EmailAlreadyUsedException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exception.getMessage());
    }

    @ExceptionHandler({StudentNotFoundException.class})
    public ResponseEntity<String> handleStudentNotFoundException(StudentNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> handleException(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }
}
