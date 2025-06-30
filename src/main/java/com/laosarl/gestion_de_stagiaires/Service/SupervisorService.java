package com.laosarl.gestion_de_stagiaires.Service;

import com.laosarl.gestion_de_stagiaires.Repository.SupervisorRepository;
import com.laosarl.gestion_de_stagiaires.domain.supervisor.Supervisor;
import com.laosarl.gestion_de_stagiaires.domain.token.Token;
import com.laosarl.gestion_de_stagiaires.domain.token.TokenType;
import com.laosarl.gestion_de_stagiaires.domain.user.CurrentUserNotFound;
import com.laosarl.gestion_de_stagiaires.domain.user.Role;
import com.laosarl.gestion_de_stagiaires.domain.user.User;
import com.laosarl.gestion_de_stagiaires.exceptions.EmailAlreadyUsedException;
import com.laosarl.gestion_de_stagiaires.exceptions.StudentNotFoundException;
import com.laosarl.gestion_de_stagiaires.Service.mapper.SupervisorMapper;
import com.laosarl.gestion_de_stagiaires.Repository.TokenRepository;
import com.laosarl.internship_management.model.AuthRequestDTO;
import com.laosarl.internship_management.model.SupervisorDTO;
import com.laosarl.internship_management.model.SupervisorIdResponseDTO;
import com.laosarl.internship_management.model.SupervisorRegistrationRequestDTO;
import com.laosarl.internship_management.model.TokenDTO;
import com.laosarl.internship_management.model.UpdateSupervisorDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SupervisorService {

    private final SupervisorRepository supervisorRepository;
    //private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenRepository tokenSpringRepository;
    private final SupervisorMapper supervisorMapper;
    //private final PasswordEncoder passwordEncoder;


    /*public TokenDTO loginUser(AuthRequestDTO authRequestDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequestDTO.getEmail(),
                        authRequestDTO.getPassword()
                )
        );

        Supervisor supervisor = supervisorRepository.findByEmail(authRequestDTO.getEmail())
                .orElseThrow(() -> new StudentNotFoundException(
                        "Student with email " + authRequestDTO.getEmail() + " not found"
                ));

        String jwtToken = jwtService.generateToken(Map.of("role", supervisor.getRole()), supervisor);
        revokeAllUserTokens(supervisor);
        saveUserToken(supervisor, jwtToken);

        return new TokenDTO().value(jwtToken);
    }*/

    private void revokeAllUserTokens(User user) {
        List<Token> allValidTokensByUser = tokenSpringRepository.findAllValidTokensByUserId(user.getId());
        if (!allValidTokensByUser.isEmpty()) {
            tokenSpringRepository.deleteAll(allValidTokensByUser);
        }
    }

    private void saveUserToken(User savedUser, String jwtToken) {
        Token token = Token.builder()
                .value(jwtToken)
                .user(savedUser)
                .type(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenSpringRepository.save(token);
    }

    public SupervisorIdResponseDTO createSupervisor(SupervisorRegistrationRequestDTO supervisorRegistrationRequestDTO) {
        Supervisor supervisor = new Supervisor();

        supervisor.setName(supervisorRegistrationRequestDTO.getName());
        supervisor.setCompanyRole(supervisorRegistrationRequestDTO.getCompanyRole());
        supervisor.setRole(Role.SUPERVISOR); // On lui donne le rôle SUPERVISOR

        Supervisor saved = supervisorRepository.save(supervisor);

        log.info("New Supervisor created with ID: {}", saved.getId());

        return new SupervisorIdResponseDTO().value(saved.getId());
    }
    @SneakyThrows
    public SupervisorDTO getUserByUsername(String username) {
        Supervisor supervisorNew = getUserByEmail(username);
        return supervisorMapper.toDTO(supervisorNew);
    }

    private Supervisor getUserByEmail(String username) throws CurrentUserNotFound {
        return supervisorRepository
                .findByEmail(username)
                .orElseThrow(() -> new CurrentUserNotFound(""));
    }

    @SneakyThrows
    public void updateUser(String username, UpdateSupervisorDTO updateSupervisorDTO) {
        Supervisor user = getUserByEmail(username);
        supervisorMapper.copyDataFromUpdateUserDTOToUser(updateSupervisorDTO, user);
        supervisorRepository.save(user);
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
                .orElseThrow(() -> new StudentNotFoundException("Student with id " + id + " not found"));
   }
}