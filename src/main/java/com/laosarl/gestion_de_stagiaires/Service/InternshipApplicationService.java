package com.laosarl.gestion_de_stagiaires.Service;

import com.laosarl.gestion_de_stagiaires.Repository.InternshipApplicationRepository;
import com.laosarl.gestion_de_stagiaires.Repository.SupervisorRepository;
import com.laosarl.gestion_de_stagiaires.Service.mapper.InternshipApplicationMapper;
import com.laosarl.gestion_de_stagiaires.domain.InternshipApplication;
import com.laosarl.gestion_de_stagiaires.domain.InternshipApplicationStatus;
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


    @Transactional
    public InternshipApplicationResponseDTO create(InternshipApplicationRequestDTO internshipApplicationRequestDTO) {
        InternshipApplication entity = internshipApplicationMapper.toEntity(internshipApplicationRequestDTO);
        entity.setSubmissionDate(LocalDate.now());
        entity.setStatus(InternshipApplicationStatus.IN_PROGRESS);
        return internshipApplicationMapper.toResponseDTO(internshipApplicationRepository.save(entity));
    }

    @Transactional(readOnly = true)
    public List<InternshipApplicationResponseDTO> getAll() {
        return internshipApplicationRepository.findAll().stream()
                .map(internshipApplicationMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public InternshipApplicationResponseDTO getById(UUID internshipId) {
        return internshipApplicationRepository.findById(internshipId)
                .map(internshipApplicationMapper::toResponseDTO)
                .orElseThrow(() -> new IllegalArgumentException("Application not found"));
    }

    @Transactional
    public void delete(UUID internshipId) {
        internshipApplicationRepository.deleteById(internshipId);
    }


    @Transactional
    public InternshipApplicationResponseDTO accept(UUID internshipId) {
        InternshipApplication internshipApplication = internshipApplicationRepository.findById(internshipId)
                .orElseThrow(() -> new IllegalArgumentException("Application not found"));

        internshipApplication.setStatus(InternshipApplicationStatus.ACCEPTED);

        mailService.sendEmail(
                internshipApplication.getEmail(),  // L'email du candidat
                "Candidature Acceptée",
                "Bonjour " + internshipApplication.getFirstName() + ",\n\nVotre candidature a été acceptée. Nous vous attendons le " + internshipApplication.getStartDate() +"Pour débuter votre Stage. Félicitations !"
        );


        return internshipApplicationMapper.toResponseDTO(internshipApplicationRepository.save(internshipApplication));
    }

    @Transactional
    public InternshipApplicationResponseDTO reject(UUID internshipId, String reason) {
        InternshipApplication internshipApplication = internshipApplicationRepository.findById(internshipId)
                .orElseThrow(() -> new IllegalArgumentException("Application not found"));

        internshipApplication.setStatus(InternshipApplicationStatus.REJECTED);
        // Optional: stocker la raison quelque part plus tard

        mailService.sendEmail(
                internshipApplication.getEmail(),
                "Candidature Refusée",
                "Nous sommes désolés de vous informer que votre candidature a été refusée. Raison : " + reason
        );

        return internshipApplicationMapper.toResponseDTO(internshipApplicationRepository.save(internshipApplication));
    }



    @Transactional
    public InternshipApplicationResponseDTO patch(UUID internshipId, UpdateInternshipApplicationDTO updateInternshipApplicationDTO) {
        InternshipApplication internshipApplication = internshipApplicationRepository.findById(internshipId)
                .orElseThrow(() -> new IllegalArgumentException("Application not found"));
        internshipApplicationMapper.updateFromDTO(updateInternshipApplicationDTO, internshipApplication);
        return internshipApplicationMapper.toResponseDTO(internshipApplicationRepository.save(internshipApplication));
    }

    @Transactional
    public void assignSupervisor(UUID internshipId, UUID id) {
        InternshipApplication internship = internshipApplicationRepository.findById(internshipId)
                .orElseThrow(() -> new EntityNotFoundException("Internship application not found"));

        if (!internship.getStatus().equals(InternshipApplicationStatus.ACCEPTED)) {
            throw new IllegalStateException("Cannot assign supervisor. Internship is not accepted.");
        }

        Supervisor supervisor = supervisorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Supervisor not found"));

        internship.setSupervisor(supervisor);
        internshipApplicationRepository.save(internship);
    }

}
