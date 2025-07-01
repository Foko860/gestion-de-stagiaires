package com.laosarl.gestion_de_stagiaires.Service;

import com.laosarl.gestion_de_stagiaires.Repository.AdminRepository;
import com.laosarl.gestion_de_stagiaires.domain.admin.Admin;
import com.laosarl.internship_management.api.AuthApi;
import com.laosarl.internship_management.model.AuthRequestDTO;
import com.laosarl.internship_management.model.TokenDTO;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    // 🔥 Authentifier un admin
    public ResponseEntity<TokenDTO> login(AuthRequestDTO authRequestDTO) {
        Admin admin = adminRepository.findByEmail(authRequestDTO.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Admin not found with email: " + authRequestDTO.getEmail()));

        if (!passwordEncoder.matches(authRequestDTO.getPassword(), admin.getPassword())) {
            throw new BadCredentialsException("Incorrect password");
        }

        String token = jwtService.generateToken(admin);

        TokenDTO tokenDTO = new TokenDTO().value(token);

        return ResponseEntity.ok(tokenDTO);
    }

    // 🔥 Charger un Admin depuis la base pour Spring Security
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return adminRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Admin not found with email: " + username));
    }

    // ✅ Créer un nouvel admin manuellement (si besoin)
    public Admin createAdmin(String name, String email, String rawPassword) {
        Admin admin = Admin.builder()
                .name(name)
                .email(email)
                .password(passwordEncoder.encode(rawPassword))
                .build();
        return adminRepository.save(admin);
    }

    // 🌟 Créer deux admins au démarrage de l'application
    @PostConstruct
    public void initDefaultAdmins() {
        if (adminRepository.count() == 0) {
            createAdmin("Super Admin", "admin1@example.com", "password1");
            createAdmin("Second Admin", "admin2@example.com", "password2");
            System.out.println("✅ Deux admins ont été créés au démarrage.");
        }
    }
}
