package com.laosarl.gestion_de_stagiaires.Service.mapper;

import com.laosarl.gestion_de_stagiaires.Model.Student;

public class StudentMapper {

    public static void update(Student source, Student target) {
        if (source.getStudyLevel() != null) target.setStudyLevel(source.getStudyLevel());
        if (source.getSpeciality() != null) target.setSpeciality(source.getSpeciality());
        if (source.getUniversity() != null) target.setUniversity(source.getUniversity());
        if (source.getRegistrationDate() != null) target.setRegistrationDate(source.getRegistrationDate());
    }
}
