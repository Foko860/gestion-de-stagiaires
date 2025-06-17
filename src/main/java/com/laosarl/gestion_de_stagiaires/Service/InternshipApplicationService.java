package com.laosarl.gestion_de_stagiaires.Service;

import com.laosarl.gestion_de_stagiaires.Repository.InternshipApplicationRepository;
import com.laosarl.gestion_de_stagiaires.Service.mapper.InternshipApplicationMapper;
import com.laosarl.gestion_de_stagiaires.domain.InternshipApplication;
import com.laosarl.gestion_de_stagiaires.domain.InternshipApplicationStatus;
import com.laosarl.internship_management.model.*;
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

    @Transactional
    public InternshipApplicationResponseDTO create(InternshipApplicationRequestDTO requestDTO) {
        InternshipApplication entity = internshipApplicationMapper.toEntity(requestDTO);
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
    public InternshipApplicationResponseDTO getById(UUID id) {
        return internshipApplicationRepository.findById(id)
                .map(internshipApplicationMapper::toResponseDTO)
                .orElseThrow(() -> new IllegalArgumentException("Application not found"));
    }

    @Transactional
    public void delete(UUID id) {
        internshipApplicationRepository.deleteById(id);
    }

    @Transactional
    public InternshipApplicationResponseDTO patch(UUID id, UpdateInternshipApplicationDTO dto) {
        InternshipApplication application = internshipApplicationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Application not found"));
        internshipApplicationMapper.updateFromDTO(dto, application);
        return internshipApplicationMapper.toResponseDTO(internshipApplicationRepository.save(application));
    }
}
