package com.laosarl.gestion_de_stagiaires.security.repository;

import com.laosarl.gestion_de_stagiaires.security.domain.student.StudentNew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface StudentSpringRepository extends JpaRepository<StudentNew, UUID> {
    Optional<StudentNew> findByEmail(String email);
    boolean existsByEmail(String email);
}
