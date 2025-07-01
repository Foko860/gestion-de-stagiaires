package com.laosarl.gestion_de_stagiaires.Controller;

import com.laosarl.gestion_de_stagiaires.Service.InternshipApplicationService;
import com.laosarl.gestion_de_stagiaires.utils.CurrentUser;
import com.laosarl.internship_management.api.InternshipApplicationApi;
import com.laosarl.internship_management.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class InternshipApplicationController implements InternshipApplicationApi {

    private final InternshipApplicationService internshipApplicationService;


    @Override
    public ResponseEntity<Void> acceptInternship(UUID internshipId) {

        internshipApplicationService.acceptInternship(internshipId, CurrentUser.getUsername());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<Void> assignSupervisor(UUID internshipId, AssignSupervisorRequestDTO assignSupervisorRequestDTO) {
        internshipApplicationService.assignSupervisor(internshipId, assignSupervisorRequestDTO.getId(), CurrentUser.getUsername());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<InternshipApplicationResponseDTO> createInternshipApplication(InternshipApplicationRequestDTO internshipApplicationRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(internshipApplicationService.create(internshipApplicationRequestDTO));
    }



    @Override
    public ResponseEntity<List<InternshipApplicationResponseDTO>> getAllInternshipApplications() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(internshipApplicationService.getAll());
    }

    @Override
    public ResponseEntity<InternshipApplicationResponseDTO> getInternshipApplicationById(UUID internshipId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(internshipApplicationService.getById(internshipId));
    }

    @Override
    public ResponseEntity<Void> deleteInternshipApplication(UUID internshipId) {
        internshipApplicationService.delete(internshipId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @Override
    public ResponseEntity<InternshipApplicationResponseDTO> patchInternshipApplication(UUID internshipId, UpdateInternshipApplicationDTO updateInternshipApplicationDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(internshipApplicationService.patch(internshipId, updateInternshipApplicationDTO));
    }

    @Override
    public ResponseEntity<Void> rejectInternshipById(UUID internshipId, ReasonDTO reasonDTO) {
        internshipApplicationService.rejectInternship(internshipId, reasonDTO, CurrentUser.getUsername());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
