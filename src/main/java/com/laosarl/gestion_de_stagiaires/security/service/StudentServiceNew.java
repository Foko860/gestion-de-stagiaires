package com.laosarl.gestion_de_stagiaires.security.service;

import com.laosarl.gestion_de_stagiaires.Service.JwtService;
import com.laosarl.gestion_de_stagiaires.domain.student.Student;
import com.laosarl.gestion_de_stagiaires.domain.token.Token;
import com.laosarl.gestion_de_stagiaires.domain.token.TokenType;
import com.laosarl.gestion_de_stagiaires.domain.user.CurrentUserNotFound;
import com.laosarl.gestion_de_stagiaires.domain.user.Role;
import com.laosarl.gestion_de_stagiaires.domain.user.User;
import com.laosarl.gestion_de_stagiaires.security.mapper.StudentMapper;
import com.laosarl.gestion_de_stagiaires.security.repository.StudentSpringRepository;
import com.laosarl.gestion_de_stagiaires.security.repository.TokenSpringRepository;
import com.laosarl.gestion_de_stagiaires.security.utils.ErrorCode;
import com.laosarl.internship_management.model.AuthRequestDTO;
import com.laosarl.internship_management.model.StudentDTO;
import com.laosarl.internship_management.model.StudentIdResponseDTO;
import com.laosarl.internship_management.model.StudentRegistrationRequestDTO;
import com.laosarl.internship_management.model.TokenDTO;
import com.laosarl.internship_management.model.UpdateUserDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceNew {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenSpringRepository tokenSpringRepository;
    private final StudentMapper studentMapper;
    private final StudentSpringRepository studentSpringRepository;
    private final PasswordEncoder passwordEncoder;

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

    @Transactional
    public TokenDTO loginUser(AuthRequestDTO authRequestDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequestDTO.getEmail(),
                        authRequestDTO.getPassword()
                )
        );

        User user = studentSpringRepository.findByEmail(authRequestDTO.getEmail()).orElseThrow();
        String jwtToken = jwtService.generateToken(Map.of("role", user.getRole()), user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        return new TokenDTO().value(jwtToken);
    }

    @Transactional
    public StudentIdResponseDTO createStudent(StudentRegistrationRequestDTO studentRegistrationRequestDTO) {
        Student student = studentMapper.toStudent(studentRegistrationRequestDTO);
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        student.setRole(Role.STUDENT);
        if (studentSpringRepository.existsByEmail(student.getEmail())) {
            throw new RuntimeException("Email already in use");
        }
        Student saved = studentSpringRepository.save(student);

        log.info("Event: new student created with ID: \n {}", saved.getId());
        return new StudentIdResponseDTO().value(saved.getId());
    }

    @SneakyThrows
    @Transactional
    public StudentDTO getUserByUsername(String username) {
        Student studentNew = getUserByEmail(username);
        return studentMapper.toDTO(studentNew);
    }

    private Student getUserByEmail(String username) throws CurrentUserNotFound {
        return studentSpringRepository
                .findByEmail(username)
                .orElseThrow(() -> new CurrentUserNotFound(ErrorCode.CURRENT_USER_NOT_FOUND));
    }

    @SneakyThrows
    @Transactional
    public void updateUser(String username, UpdateUserDTO updateUserDTO) {
        Student user = getUserByEmail(username);
        studentMapper.copyDataFromUpdateUserDTOToUser(updateUserDTO, user);
        studentSpringRepository.save(user);
    }
}
