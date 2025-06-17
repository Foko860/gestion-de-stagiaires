package com.laosarl.gestion_de_stagiaires.Controller;

import com.laosarl.gestion_de_stagiaires.domain.InternshipApplication;
//import com.laosarl.gestion_de_stagiaires.Service.InternshipApplicationService;
import com.laosarl.internship_management.api.InternshipApplicationApi;
import com.laosarl.internship_management.model.InternshipApplicationRequestDTO;
import com.laosarl.internship_management.model.InternshipApplicationResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController

public abstract class InternshipApplicationController implements InternshipApplicationApi {
    //private final InternshipApplicationService internshipApplicationService;

  /*  @Override
    public ResponseEntity<InternshipApplicationResponseDTO> createInternshipApplication(InternshipApplicationRequestDTO internshipApplicationRequestDTO) {
        return ResponseEntity.status(201).body(internshipApplicationService.create(request));
    }*/

    @Override
    public ResponseEntity<Void> deleteInternshipApplication(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<List<InternshipApplicationResponseDTO>> getAllInternshipApplications() {
        return null;
    }

    @Override
    public ResponseEntity<InternshipApplicationResponseDTO> getInternshipApplicationById(UUID id) {
        return null;
    }


}
