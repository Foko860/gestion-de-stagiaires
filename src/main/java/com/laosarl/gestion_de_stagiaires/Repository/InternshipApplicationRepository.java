package com.laosarl.gestion_de_stagiaires.Repository;

import com.laosarl.gestion_de_stagiaires.domain.InternshipApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InternshipApplicationRepository extends JpaRepository<InternshipApplication, UUID> {
}
