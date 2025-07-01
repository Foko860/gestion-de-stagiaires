package com.laosarl.gestion_de_stagiaires.Repository;

import com.laosarl.gestion_de_stagiaires.domain.admin.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, String> {
    Optional<Admin> findByEmail(String email);
    boolean existsByEmail(String email);
}
