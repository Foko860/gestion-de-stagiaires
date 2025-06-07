package com.laosarl.gestion_de_stagiaires.Service.mapper;

import com.laosarl.gestion_de_stagiaires.Model.Student;
import com.laosarl.internship_management.model.StudentRequestDTO;
import com.laosarl.internship_management.model.StudentResponseDTO;

public class StudentMapper {

    public static Student fromRequestDTO(StudentRequestDTO studentRequestDTO) {
        Student student = new Student();
        // Mappage des champs de base
        student.setUsername(studentRequestDTO.getUsername());
        student.setEmail(studentRequestDTO.getEmail());
        student.setFirstName(studentRequestDTO.getFirstName());
        student.setLastName(studentRequestDTO.getLastName());

        // Mappage des champs spécifiques
        student.setStudyLevel(studentRequestDTO.getStudyLevel());
        student.setSpeciality(studentRequestDTO.getSpeciality());
        student.setUniversity(studentRequestDTO.getUniversity());
        student.setRegistrationDate(studentRequestDTO.getRegistrationDate());

        return student;
    }

    public static StudentResponseDTO toResponseDTO(Student entity) {
        StudentResponseDTO studentResponseDTO = new StudentResponseDTO();
        // Mappage des champs de base
        studentResponseDTO.setId(entity.getId());
        studentResponseDTO.setUsername(entity.getUsername());
        studentResponseDTO.setEmail(entity.getEmail());
        studentResponseDTO.setFirstName(entity.getFirstName());
        studentResponseDTO.setLastName(entity.getLastName());

        // Mappage des champs spécifiques
        studentResponseDTO.setStudyLevel(entity.getStudyLevel());
        studentResponseDTO.setSpeciality(entity.getSpeciality());
        studentResponseDTO.setUniversity(entity.getUniversity());
        studentResponseDTO.setRegistrationDate(entity.getRegistrationDate());

        return studentResponseDTO;
    }

    public static void update(Student source, Student target) {
        if (source.getStudyLevel() != null) target.setStudyLevel(source.getStudyLevel());
        if (source.getSpeciality() != null) target.setSpeciality(source.getSpeciality());
        if (source.getUniversity() != null) target.setUniversity(source.getUniversity());
        if (source.getRegistrationDate() != null) target.setRegistrationDate(source.getRegistrationDate());
        // Ajouter les autres champs si nécessaire
    }
}