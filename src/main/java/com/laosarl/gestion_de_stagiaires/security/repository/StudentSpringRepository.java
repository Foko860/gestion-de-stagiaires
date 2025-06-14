package com.laosarl.gestion_de_stagiaires.security.repository;

import com.laosarl.gestion_de_stagiaires.domain.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface StudentSpringRepository extends JpaRepository<Student, UUID> {
    Optional<Student> findByEmail(String email);
    boolean existsByEmail(String email);
}
