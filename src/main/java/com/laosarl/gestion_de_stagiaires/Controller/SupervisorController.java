package com.laosarl.gestion_de_stagiaires.Controller;

import com.laosarl.gestion_de_stagiaires.Service.SupervisorService;
import com.laosarl.internship_management.api.AuthApi;
import com.laosarl.internship_management.api.SupervisorApi;
import com.laosarl.internship_management.model.AuthRequestDTO;
import com.laosarl.internship_management.model.SupervisorDTO;
import com.laosarl.internship_management.model.SupervisorIdResponseDTO;
import com.laosarl.internship_management.model.SupervisorRegistrationRequestDTO;
import com.laosarl.internship_management.model.TokenDTO;
import com.laosarl.internship_management.model.UpdateSupervisorDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public  class SupervisorController implements AuthApi, SupervisorApi {

    private static String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private final SupervisorService supervisorService;

    @Override
    public ResponseEntity<SupervisorIdResponseDTO> createSupervisor(
            SupervisorRegistrationRequestDTO supervisorRegistrationRequestDTOO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(supervisorService.createSupervisor(supervisorRegistrationRequestDTOO));
    }

    @Override
    public ResponseEntity<Void> deleteSupervisor(UUID id) {
        supervisorService.deleteSupervisor(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<SupervisorDTO>> getAllSupervisors() {
        return null;
    }

    /*@Override
    public ResponseEntity<SupervisorDTO> getCurrentSupervisor() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(supervisorService.getUserByUsername(getCurrentUsername()));

    }*/

    @Override
    public ResponseEntity<SupervisorDTO> getSupervisorById(UUID id) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(supervisorService.getSupervisorById(id));
    }

    @Override
    public ResponseEntity<TokenDTO> loginUser(AuthRequestDTO authRequestDTO) {
        return ResponseEntity.ok(supervisorService.loginUser(authRequestDTO));
    }

    @Override
    public ResponseEntity<Void> updateSupervisor(UUID id, UpdateSupervisorDTO updateSupervisorDTO) {
        supervisorService.updateSupervisor(id, updateSupervisorDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}