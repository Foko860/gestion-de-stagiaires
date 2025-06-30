package com.laosarl.gestion_de_stagiaires.Service;

import com.laosarl.gestion_de_stagiaires.Repository.DocumentRepository;
import com.laosarl.gestion_de_stagiaires.Repository.InternshipApplicationRepository;
import com.laosarl.gestion_de_stagiaires.Repository.SupervisorRepository;
import com.laosarl.gestion_de_stagiaires.Service.mapper.InternshipApplicationMapper;
import com.laosarl.gestion_de_stagiaires.domain.InternshipApplication;
import com.laosarl.gestion_de_stagiaires.domain.InternshipApplicationStatus;
import com.laosarl.gestion_de_stagiaires.domain.document.Document;
import com.laosarl.gestion_de_stagiaires.domain.supervisor.Supervisor;
import com.laosarl.internship_management.model.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InternshipApplicationService {

    private final InternshipApplicationRepository internshipApplicationRepository;
    private final InternshipApplicationMapper internshipApplicationMapper;
    private final SupervisorRepository supervisorRepository;
    private final MailService mailService;
    private final DocumentRepository documentRepository;

    // ✅ Création d'une candidature
    @Transactional
    public InternshipApplicationResponseDTO create(InternshipApplicationRequestDTO internshipApplicationRequestDTO) {
        InternshipApplication entity = internshipApplicationMapper.toEntity(internshipApplicationRequestDTO, documentRepository);
        entity.setSubmissionDate(LocalDate.now());
        entity.setStatus(InternshipApplicationStatus.IN_PROGRESS);
        return internshipApplicationMapper.toResponseDTO(internshipApplicationRepository.save(entity));
    }

    // ✅ Récupérer toutes les candidatures
    @Transactional(readOnly = true)
    public List<InternshipApplicationResponseDTO> getAll() {
        return internshipApplicationRepository.findAll().stream()
                .map(internshipApplicationMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // ✅ Récupérer une candidature par ID
    @Transactional(readOnly = true)
    public InternshipApplicationResponseDTO getById(UUID internshipId) {
        return internshipApplicationRepository.findById(internshipId)
                .map(internshipApplicationMapper::toResponseDTO)
                .orElseThrow(() -> new IllegalArgumentException("Application not found"));
    }

    // ✅ Suppression
    @Transactional
    public void delete(UUID internshipId) {
        internshipApplicationRepository.deleteById(internshipId);
    }

    // ✅ Acceptation avec envoi d'email
    @Transactional
    public InternshipApplicationResponseDTO accept(UUID internshipId) {
        InternshipApplication internshipApplication = internshipApplicationRepository.findById(internshipId)
                .orElseThrow(() -> new IllegalArgumentException("Application not found"));

        internshipApplication.setStatus(InternshipApplicationStatus.ACCEPTED);

        mailService.sendEmail(
                internshipApplication.getEmail(),
                "Candidature Acceptée",
                "Bonjour " + internshipApplication.getFirstName() + ",\n\nVotre candidature a été acceptée. "
                        + "Nous vous attendons le " + internshipApplication.getStartDate() + " pour débuter votre stage.\nFélicitations !"
        );

        return internshipApplicationMapper.toResponseDTO(internshipApplicationRepository.save(internshipApplication));
    }

    // ✅ Rejet avec envoi d'email
    @Transactional
    public InternshipApplicationResponseDTO reject(UUID internshipId, String reason) {
        InternshipApplication internshipApplication = internshipApplicationRepository.findById(internshipId)
                .orElseThrow(() -> new IllegalArgumentException("Application not found"));

        internshipApplication.setStatus(InternshipApplicationStatus.REJECTED);

        mailService.sendEmail(
                internshipApplication.getEmail(),
                "Candidature Refusée",
                "Bonjour " + internshipApplication.getFirstName() + ",\n\nNous sommes désolés de vous informer que votre candidature "
                        + "a été refusée.\nRaison : " + reason + "\n\nCordialement."
        );

        return internshipApplicationMapper.toResponseDTO(internshipApplicationRepository.save(internshipApplication));
    }

    // ✅ Mise à jour partielle (PATCH)
    @Transactional
    public InternshipApplicationResponseDTO patch(UUID internshipId, UpdateInternshipApplicationDTO updateInternshipApplicationDTO) {
        InternshipApplication internshipApplication = internshipApplicationRepository.findById(internshipId)
                .orElseThrow(() -> new IllegalArgumentException("Application not found"));

        internshipApplicationMapper.updateFromDTO(updateInternshipApplicationDTO, internshipApplication, documentRepository);

        return internshipApplicationMapper.toResponseDTO(internshipApplicationRepository.save(internshipApplication));
    }

    // ✅ Assignation d'un encadreur
    @Transactional
    public void assignSupervisor(UUID internshipId, UUID supervisorId) {
        InternshipApplication internshipApplication = internshipApplicationRepository.findById(internshipId)
                .orElseThrow(() -> new EntityNotFoundException("Internship application not found"));

        if (!internshipApplication.getStatus().equals(InternshipApplicationStatus.ACCEPTED)) {
            throw new IllegalStateException("Cannot assign supervisor. Internship is not accepted.");
        }

        Supervisor supervisor = supervisorRepository.findById(supervisorId)
                .orElseThrow(() -> new EntityNotFoundException("Supervisor not found"));

        internshipApplication.setSupervisor(supervisor);
        internshipApplicationRepository.save(internshipApplication);
    }
}
