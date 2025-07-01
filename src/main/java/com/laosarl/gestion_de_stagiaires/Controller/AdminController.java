package com.laosarl.gestion_de_stagiaires.Controller;

import com.laosarl.gestion_de_stagiaires.Service.AdminService;
import com.laosarl.internship_management.api.AuthApi;
import com.laosarl.internship_management.model.AuthRequestDTO;
import com.laosarl.internship_management.model.TokenDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminController implements AuthApi {

    private final AdminService adminService;

    @Override
    public ResponseEntity<TokenDTO> loginUser(AuthRequestDTO authRequestDTO) {
        return adminService.login(authRequestDTO);
    }
}
