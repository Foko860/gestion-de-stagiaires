package com.laosarl.gestion_de_stagiaires.Service;

import com.laosarl.gestion_de_stagiaires.Repository.StudentRepository;
import com.laosarl.gestion_de_stagiaires.domain.student.Student;
import com.laosarl.gestion_de_stagiaires.domain.token.Token;
import com.laosarl.gestion_de_stagiaires.domain.token.TokenType;
import com.laosarl.gestion_de_stagiaires.domain.user.CurrentUserNotFound;
import com.laosarl.gestion_de_stagiaires.domain.user.Role;
import com.laosarl.gestion_de_stagiaires.domain.user.User;
import com.laosarl.gestion_de_stagiaires.exceptions.EmailAlreadyUsedException;
import com.laosarl.gestion_de_stagiaires.exceptions.StudentNotFoundException;
import com.laosarl.gestion_de_stagiaires.Service.mapper.StudentMapper;
import com.laosarl.gestion_de_stagiaires.Repository.TokenRepository;
import com.laosarl.internship_management.model.AuthRequestDTO;
import com.laosarl.internship_management.model.StudentDTO;
import com.laosarl.internship_management.model.StudentIdResponseDTO;
import com.laosarl.internship_management.model.StudentRegistrationRequestDTO;
import com.laosarl.internship_management.model.TokenDTO;
import com.laosarl.internship_management.model.UpdateStudentDTO;
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
public class StudentService {

    private final StudentRepository studentRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenRepository tokenSpringRepository;
    private final StudentMapper studentMapper;
    private final PasswordEncoder passwordEncoder;


    public TokenDTO loginUser(AuthRequestDTO authRequestDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequestDTO.getEmail(),
                        authRequestDTO.getPassword()
                )
        );

        Student student = studentRepository.findByEmail(authRequestDTO.getEmail())
                .orElseThrow(() -> new StudentNotFoundException(
                        "Student with email " + authRequestDTO.getEmail() + " not found"
                ));

        String jwtToken = jwtService.generateToken(Map.of("role", student.getRole()), student);
        revokeAllUserTokens(student);
        saveUserToken(student, jwtToken);

        return new TokenDTO().value(jwtToken);
    }

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

    public StudentIdResponseDTO createStudent(StudentRegistrationRequestDTO studentRegistrationRequestDTO) {
        if (studentRepository.existsByEmail((studentRegistrationRequestDTO.getEmail()))) {
            throw new EmailAlreadyUsedException("Email already in use");
        }
        Student student = studentMapper.toStudent(studentRegistrationRequestDTO);
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        student.setRole(Role.STUDENT);

        Student saved = studentRepository.save(student);

        log.info("Event: new student created with ID: \n {}", saved.getId());
        return new StudentIdResponseDTO().value(saved.getId());
    }

    @SneakyThrows
    public StudentDTO getUserByUsername(String username) {
        Student studentNew = getUserByEmail(username);
        return studentMapper.toDTO(studentNew);
    }

    private Student getUserByEmail(String username) throws CurrentUserNotFound {
        return studentRepository
                .findByEmail(username)
                .orElseThrow(() -> new CurrentUserNotFound(""));
    }

    @SneakyThrows
    public void updateUser(String username, UpdateStudentDTO updateStudentDTO) {
        Student user = getUserByEmail(username);
        studentMapper.copyDataFromUpdateUserDTOToUser(updateStudentDTO, user);
        studentRepository.save(user);
    }


    public void deleteStudent(UUID id) {
        studentRepository.deleteById(id);
    }

    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(studentMapper::toDTO)
                .toList();
    }

    public StudentDTO getStudentById(UUID id) {
        return studentRepository.findById(id)
                .map(studentMapper::toDTO)
                .orElseThrow(() -> new StudentNotFoundException("Student with id " + id + " not found"));
   }
}