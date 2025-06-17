package com.laosarl.gestion_de_stagiaires.Controller;

import com.laosarl.gestion_de_stagiaires.Service.InternshipApplicationService;
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
    public ResponseEntity<InternshipApplicationResponseDTO> getInternshipApplicationById(UUID id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(internshipApplicationService.getById(id));
    }

    @Override
    public ResponseEntity<Void> deleteInternshipApplication(UUID id) {
        internshipApplicationService.delete(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @Override
    public ResponseEntity<InternshipApplicationResponseDTO> patchInternshipApplication(UUID id, UpdateInternshipApplicationDTO dto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(internshipApplicationService.patch(id, dto));
    }
}
