package com.laosarl.gestion_de_stagiaires.Controller;

import com.laosarl.gestion_de_stagiaires.Model.Student;
import com.laosarl.gestion_de_stagiaires.Service.StudentService;
//import com.laosarl.gestion_de_stagiaires.Service.mapper.StudentMapperOld;
import com.laosarl.internship_management.api.StudentApi;
import com.laosarl.internship_management.model.CreateStudentRequestDTO;
import com.laosarl.internship_management.model.StudentIdResponseDTO;
import com.laosarl.internship_management.model.StudentResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public abstract class StudentController implements StudentApi {

    private final StudentService studentService;

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