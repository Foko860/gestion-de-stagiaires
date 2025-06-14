package com.laosarl.gestion_de_stagiaires.Controller;

import com.laosarl.gestion_de_stagiaires.Service.StudentService;
//import com.laosarl.gestion_de_stagiaires.Service.mapper.StudentMapperOld;
import com.laosarl.internship_management.api.AuthApi;
import com.laosarl.internship_management.api.StudentApi;
import com.laosarl.internship_management.model.AuthRequestDTO;
import com.laosarl.internship_management.model.CreateStudentRequestDTO;
import com.laosarl.internship_management.model.StudentDTO;
import com.laosarl.internship_management.model.StudentIdResponseDTO;
import com.laosarl.internship_management.model.StudentRegistrationRequestDTO;
import com.laosarl.internship_management.model.StudentResponseDTO;
import com.laosarl.internship_management.model.TokenDTO;
import com.laosarl.internship_management.model.UpdateStudentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class StudentController implements AuthApi, StudentApi {

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
    public ResponseEntity<List<StudentResponseDTO>> getAllStudents() {
        return null;
    }

    @Override
    public ResponseEntity<StudentDTO> getCurrentStudent() {
        return null;
    }

    @Override
    public ResponseEntity<StudentResponseDTO> getStudentById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<TokenDTO> loginUserAuth(AuthRequestDTO authRequestDTO) {
        return ResponseEntity.ok(studentService.loginUser(authRequestDTO));
    }

    @Override
    public ResponseEntity<Void> updateStudent(UpdateStudentDTO updateStudentDTO) {
        return null;
    }

//    @Override
//    public ResponseEntity<StudentResponseDTO> createStudent(StudentRequestDTO studentRequestDTO) {
//        Student student = StudentMapper.fromRequestDTO(studentRequestDTO);
//        Student savedStudent = studentService.createStudent(student);
//        return ResponseEntity
//                .status(201)
//                .body(StudentMapper.toResponseDTO(savedStudent));
//    }

//    @Override
//    public ResponseEntity<List<StudentResponseDTO>> getAllStudents() {
//        List<StudentResponseDTO> students = studentService.getAllStudents().stream()
//                .map(StudentMapper::toResponseDTO)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(students);
//    }

//    @Override
//    public ResponseEntity<StudentResponseDTO> getStudentById(Long id) {
//        return studentService.getStudentById(id)
//                .map(student -> ResponseEntity.ok(StudentMapper.toResponseDTO(student)))
//                .orElse(ResponseEntity.notFound().build());
//    }

//    @Override
//    public ResponseEntity<StudentResponseDTO> updateStudent(Long id, StudentRequestDTO studentRequestDTO) {
//        Student updatedStudentEntity = StudentMapper.fromRequestDTO(studentRequestDTO);
//        Student updatedStudent = studentService.updateStudent(id, updatedStudentEntity);
//        return ResponseEntity.ok(StudentMapper.toResponseDTO(updatedStudent));
//    }

//    @Override
//    public ResponseEntity<Void> deleteStudent(Long id) {
//        studentService.deleteStudent(id);
//        return ResponseEntity.noContent().build();
//    }
}