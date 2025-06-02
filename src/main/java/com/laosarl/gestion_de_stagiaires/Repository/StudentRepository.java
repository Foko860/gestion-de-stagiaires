package com.laosarl.gestion_de_stagiaires.Repository;

import com.laosarl.gestion_de_stagiaires.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

}