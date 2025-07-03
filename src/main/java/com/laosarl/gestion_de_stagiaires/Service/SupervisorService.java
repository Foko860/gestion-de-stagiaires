package com.laosarl.gestion_de_stagiaires.Service;

import com.laosarl.gestion_de_stagiaires.Repository.SupervisorRepository;
import com.laosarl.gestion_de_stagiaires.Service.mapper.SupervisorMapper;
import com.laosarl.gestion_de_stagiaires.domain.supervisor.Supervisor;
import com.laosarl.gestion_de_stagiaires.domain.user.Role;
import com.laosarl.gestion_de_stagiaires.exceptions.ResourceNotFoundException;
import com.laosarl.internship_management.model.SupervisorDTO;
import com.laosarl.internship_management.model.SupervisorIdResponseDTO;
import com.laosarl.internship_management.model.SupervisorRegistrationRequestDTO;
import com.laosarl.internship_management.model.UpdateSupervisorDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SupervisorService {

    private final SupervisorRepository supervisorRepository;
    private final SupervisorMapper supervisorMapper;

    //TODO: add the admin creator in process of supervisor creation
    public SupervisorIdResponseDTO createSupervisor(SupervisorRegistrationRequestDTO supervisorRegistrationRequestDTO,
                                                    String username) {
        Supervisor supervisor = new Supervisor();

        supervisor.setName(supervisorRegistrationRequestDTO.getName());
        supervisor.setCompanyRole(supervisorRegistrationRequestDTO.getCompanyRole());
        supervisor.setRole(Role.SUPERVISOR); // On lui donne le rôle SUPERVISOR

        Supervisor saved = supervisorRepository.save(supervisor);

        log.info("New Supervisor created with ID: {}", saved.getId());

        return new SupervisorIdResponseDTO().value(saved.getId());
    }


    public void updateSupervisor(UUID id, UpdateSupervisorDTO updateSupervisorDTO) {
        Supervisor supervisor = supervisorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Supervisor not found with id: " + id));

        // Mapper les champs depuis UpdateSupervisorDTO vers l'entité Supervisor
        supervisorMapper.copyDataFromUpdateUserDTOToUser(updateSupervisorDTO, supervisor);

        supervisorRepository.save(supervisor);

    }

    public void deleteSupervisor(UUID id) {
        supervisorRepository.deleteById(id);
    }

    public List<SupervisorDTO> getAllSupervisors() {
        return supervisorRepository.findAll().stream()
                .map(supervisorMapper::toDTO)
                .toList();
    }

    public SupervisorDTO getSupervisorById(UUID id) {
        return supervisorRepository.findById(id)
                .map(supervisorMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id " + id + " not found"));
    }
}