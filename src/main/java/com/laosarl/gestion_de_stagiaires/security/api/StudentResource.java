package com.laosarl.gestion_de_stagiaires.security.api;

import com.laosarl.gestion_de_stagiaires.security.service.StudentServiceNew;
import com.laosarl.gestion_de_stagiaires.security.utils.CurrentUserUtils;
import com.laosarl.internship_management.api.AuthApi;
import com.laosarl.internship_management.model.AuthRequestDTO;
import com.laosarl.internship_management.model.StudentDTO;
import com.laosarl.internship_management.model.StudentIdResponseDTO;
import com.laosarl.internship_management.model.StudentRegistrationRequestDTO;
import com.laosarl.internship_management.model.TokenDTO;
import com.laosarl.internship_management.model.UpdateUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StudentResource implements AuthApi {
    private final StudentServiceNew studentServiceNew;

    private static String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public ResponseEntity<TokenDTO> loginUserAuth(AuthRequestDTO authRequestDTO) {
        return ResponseEntity.ok(studentServiceNew.loginUser(authRequestDTO));
    }

    @Override
    public ResponseEntity<StudentIdResponseDTO> createStudentAuth(StudentRegistrationRequestDTO studentRegistrationRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(studentServiceNew.createStudent(studentRegistrationRequestDTO));
    }

    @Override
    public ResponseEntity<StudentDTO> getCurrentStudentAuth() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(studentServiceNew.getUserByUsername(CurrentUserUtils.getUsername()));
    }

    @Override
    public ResponseEntity<Void> updateUserAuth(UpdateUserDTO updateUserDTO) {
        studentServiceNew.updateUser(getCurrentUsername(), updateUserDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
