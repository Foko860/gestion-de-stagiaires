package com.laosarl.gestion_de_stagiaires.Controller;

import com.laosarl.gestion_de_stagiaires.Service.StudentService;
import com.laosarl.internship_management.api.AuthApi;
import com.laosarl.internship_management.api.StudentApi;
import com.laosarl.internship_management.model.AuthRequestDTO;
import com.laosarl.internship_management.model.StudentDTO;
import com.laosarl.internship_management.model.StudentIdResponseDTO;
import com.laosarl.internship_management.model.StudentRegistrationRequestDTO;
import com.laosarl.internship_management.model.TokenDTO;
import com.laosarl.internship_management.model.UpdateStudentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public abstract class StudentController implements AuthApi, StudentApi {

    private static String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private final StudentService studentService;

    @Override
    public ResponseEntity<StudentIdResponseDTO> createStudent(
            StudentRegistrationRequestDTO studentRegistrationRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(studentService.createStudent(studentRegistrationRequestDTO));
    }

    @Override
    public ResponseEntity<Void> deleteStudent(UUID id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<StudentDTO> getCurrentStudent() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(studentService.getUserByUsername(getCurrentUsername()));

    }

    @Override
    public ResponseEntity<StudentDTO> getStudentById(UUID id) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(studentService.getStudentById(id));
    }

    @Override
    public ResponseEntity<TokenDTO> loginUser(AuthRequestDTO authRequestDTO) {
        return ResponseEntity.ok(studentService.loginUser(authRequestDTO));
    }

    @Override
    public ResponseEntity<Void> updateStudent(UpdateStudentDTO updateStudentDTO) {
        studentService.updateUser(getCurrentUsername(), updateStudentDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}