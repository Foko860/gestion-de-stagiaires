package com.laosarl.gestion_de_stagiaires.Service;

import com.laosarl.gestion_de_stagiaires.Model.Student;
import com.laosarl.gestion_de_stagiaires.Repository.StudentRepository;
import com.laosarl.internship_management.model.CreateStudentRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;
//    private final UserMapper userMapper;

//    public Student createStudent(CreateStudentRequestDTO createStudentRequestDTO) {
//        Student student = userMapper.toStudent(createStudentRequestDTO);
//        return studentRepository.save(student);
//    }
//
//    @Transactional(readOnly = true)
//    public List<Student> getAllStudents() {
//        return studentRepository.findAll();
//    }
//
//    @Transactional(readOnly = true)
//    public Optional<Student> getStudentById(Long id) { // Changement pour retourner Optional
//        return studentRepository.findById(id);
//    }
//
//    public Student updateStudent(Long id, Student newData) {
//        Student existing = studentRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Student not found"));
//        StudentMapperOld.update(newData, existing);
//        return studentRepository.save(existing);
//    }
//
//    public void deleteStudent(Long id) {
//        studentRepository.deleteById(id);
//    }
}