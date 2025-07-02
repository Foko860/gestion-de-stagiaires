package com.laosarl.gestion_de_stagiaires.Service;

import com.laosarl.gestion_de_stagiaires.Repository.AdminRepository;
import com.laosarl.gestion_de_stagiaires.Repository.DocumentRepository;
import com.laosarl.gestion_de_stagiaires.Repository.InternshipApplicationRepository;
import com.laosarl.gestion_de_stagiaires.Repository.SupervisorRepository;
import com.laosarl.gestion_de_stagiaires.Service.mapper.InternshipApplicationMapper;
import com.laosarl.gestion_de_stagiaires.domain.InternshipApplication;
import com.laosarl.gestion_de_stagiaires.domain.InternshipApplicationStatus;
import com.laosarl.gestion_de_stagiaires.domain.admin.Admin;
import com.laosarl.gestion_de_stagiaires.domain.supervisor.Supervisor;
import com.laosarl.gestion_de_stagiaires.exceptions.ResourceNotFoundException;
import com.laosarl.internship_management.model.InternshipApplicationRequestDTO;
import com.laosarl.internship_management.model.InternshipApplicationResponseDTO;
import com.laosarl.internship_management.model.ReasonDTO;
import com.laosarl.internship_management.model.UpdateInternshipApplicationDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InternshipApplicationService {

    private final InternshipApplicationRepository internshipApplicationRepository;
    private final InternshipApplicationMapper internshipApplicationMapper;
    private final SupervisorRepository supervisorRepository;
    private final MailService mailService;
    private final DocumentRepository documentRepository;
    private final AdminRepository adminRepository;

    // ✅ Création d'une candidature
    @Transactional
    public InternshipApplicationResponseDTO create(InternshipApplicationRequestDTO internshipApplicationRequestDTO) {
        InternshipApplication entity = internshipApplicationMapper.toEntity(internshipApplicationRequestDTO, documentRepository);
        entity.setSubmissionDate(LocalDate.now());
        entity.setStatus(InternshipApplicationStatus.PUBLISHED);
        return internshipApplicationMapper.toResponseDTO(internshipApplicationRepository.save(entity));
    }

    // ✅ Récupérer toutes les candidatures
    @Transactional(readOnly = true)
    public List<InternshipApplicationResponseDTO> getAll() {
        return internshipApplicationRepository.findAll().stream()
                .map(internshipApplicationMapper::toResponseDTO)
                .toList();
    }

    // ✅ Récupérer une candidature par ID
    @Transactional(readOnly = true)
    public InternshipApplicationResponseDTO getById(UUID internshipId) {
        return internshipApplicationRepository.findById(internshipId)
                .map(internshipApplicationMapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Internship Application Not found"));
    }

    // ✅ Suppression
    @Transactional
    public void delete(UUID internshipId) {
        internshipApplicationRepository.deleteById(internshipId);
    }

    // ✅ Acceptation avec envoi d'email
    @Transactional
    public void acceptInternship(UUID internshipId, String username) {
        InternshipApplication internshipApplication = internshipApplicationRepository.findById(internshipId)
                .orElseThrow(() -> new ResourceNotFoundException("Internship Application Not found"));
        Admin admin = adminRepository.findByEmail(username).orElseThrow();


        internshipApplication.markHasAccept(admin);
        internshipApplicationRepository.save(internshipApplication);

        mailService.sendEmail(
                internshipApplication.getEmail(),
                "Candidature Acceptée",
                "Bonjour " + internshipApplication.getFirstName() + internshipApplication.getLastName() + ",\n\nVotre candidature a été acceptée. "
                        + "Nous vous attendons le " + internshipApplication.getStartDate() + " pour débuter votre stage.\nFélicitations et Bienvenue à LAO SARL !"
        );
    }

    // ✅ Rejet avec envoi d'email
    @Transactional
    public void rejectInternship(UUID internshipId,
                                 ReasonDTO reason,
                                 String username) {
        InternshipApplication internshipApplication = internshipApplicationRepository.findById(internshipId)
                .orElseThrow(() -> new ResourceNotFoundException("Internship Application Not found"));
        Admin admin = adminRepository.findByEmail(username).orElseThrow();

        internshipApplication.markHasRejected(admin, reason.getDescription());
        internshipApplicationRepository.save(internshipApplication);

        mailService.sendEmail(
                internshipApplication.getEmail(),
                "Candidature Refusée",
                "Bonjour " + internshipApplication.getFirstName() + internshipApplication.getLastName() + ",\n\nNous sommes désolés de vous informer que votre candidature "
                        + "a été refusée.\nRaison : " + reason.getDescription() + "\n\nCordialement."
        );
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
    public void assignSupervisor(UUID internshipId, UUID supervisorId, String username) {
        InternshipApplication internshipApplication = internshipApplicationRepository.findById(internshipId)
                .orElseThrow(() -> new EntityNotFoundException("Internship application not found"));
        Supervisor supervisor = supervisorRepository.findById(supervisorId)
                .orElseThrow(() -> new ResourceNotFoundException("Supervisor not found"));
        Admin admin = adminRepository.findByEmail(username).orElseThrow();

        internshipApplication.assignSupervisor(supervisor, admin);

        internshipApplicationRepository.save(internshipApplication);
    }
}
