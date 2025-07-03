package com.laosarl.gestion_de_stagiaires.Service;

import com.laosarl.gestion_de_stagiaires.Repository.AdminRepository;
import com.laosarl.gestion_de_stagiaires.Repository.DocumentRepository;
import com.laosarl.gestion_de_stagiaires.Repository.InternshipApplicationRepository;
import com.laosarl.gestion_de_stagiaires.Repository.SupervisorRepository;
import com.laosarl.gestion_de_stagiaires.Service.mapper.InternshipApplicationMapper;
import com.laosarl.gestion_de_stagiaires.domain.InternshipApplication;
import com.laosarl.gestion_de_stagiaires.domain.admin.Admin;
import com.laosarl.gestion_de_stagiaires.domain.supervisor.Supervisor;
import com.laosarl.gestion_de_stagiaires.exceptions.ResourceNotFoundException;
import com.laosarl.internship_management.model.InternshipApplicationDTO;
import com.laosarl.internship_management.model.InternshipApplicationIdDTO;
import com.laosarl.internship_management.model.InternshipApplicationRequestDTO;
import com.laosarl.internship_management.model.ReasonDTO;
import com.laosarl.internship_management.model.UpdateInternshipApplicationDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public InternshipApplicationIdDTO create(InternshipApplicationRequestDTO dto) {
        if (!documentRepository.existsById(dto.getDocumentId())) {
            throw new ResourceNotFoundException("Document not found");
        }
        InternshipApplication entity = internshipApplicationMapper.toEntity(dto);
        entity.markHasPushed();

        InternshipApplication saved = internshipApplicationRepository.save(entity);
        return new InternshipApplicationIdDTO().value(saved.getInternshipId());
    }

    // ✅ Récupérer toutes les candidatures
    @Transactional(readOnly = true)
    public List<InternshipApplicationDTO> getAll() {
        return internshipApplicationRepository.findAll().stream()
                .map(internshipApplicationMapper::toResponseDTO)
                .toList();
    }

    // ✅ Récupérer une candidature par ID
    @Transactional(readOnly = true)
    public InternshipApplicationDTO getById(UUID internshipId) {
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

    // ✅ Mise à jour
    @Transactional
    public void updateInternshipApplication(UUID internshipId, UpdateInternshipApplicationDTO updateInternshipApplicationDTO) {
        InternshipApplication internshipApplication = internshipApplicationRepository.findById(internshipId)
                .orElseThrow(() -> new ResourceNotFoundException("InternshipApplication not found"));

        internshipApplicationMapper.updateFromDTO(updateInternshipApplicationDTO, internshipApplication);
        internshipApplicationRepository.save(internshipApplication);
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
