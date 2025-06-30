package com.laosarl.gestion_de_stagiaires.Repository;

import com.laosarl.gestion_de_stagiaires.domain.document.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DocumentRepository extends JpaRepository<Document, UUID> {
}
