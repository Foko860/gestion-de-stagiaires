package com.laosarl.gestion_de_stagiaires.Controller;

import com.laosarl.internship_management.api.UserApi;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public abstract class UserController implements UserApi {

//    private final UserService userService;
//
//    @Override
//    public ResponseEntity<UserResponseDTO> createUser(UserRequestDTO userRequestDTO) {
//        // Conversion DTO -> Entity dans le contrôleur
//        User user = UserMapper.fromRequestDTO(userRequestDTO);
//        User savedUser = userService.createUser(user);
//        return ResponseEntity
//                .status(201)
//                .body(UserMapper.toResponseDTO(savedUser));
//    }
//
//    @Override
//    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
//        List<UserResponseDTO> users = userService.getAllUsers().stream()
//                .map(UserMapper::toResponseDTO)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(users);
//    }
//
//    @Override
//    public ResponseEntity<UserResponseDTO> getUserById(Long id) {
//        return userService.getUserById(id)
//                .map(user -> ResponseEntity.ok(UserMapper.toResponseDTO(user)))
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    @Override
//    public ResponseEntity<UserResponseDTO> updateUser(Long id, UserRequestDTO userRequestDTO) {
//        // Conversion DTO -> Entity dans le contrôleur
//        User updatedUserEntity = UserMapper.fromRequestDTO(userRequestDTO);
//        User updatedUser = userService.updateUser(id, updatedUserEntity);
//        return ResponseEntity.ok(UserMapper.toResponseDTO(updatedUser));
//    }
//
//    @Override
//    public ResponseEntity<Void> deleteUser(Long id) {
//        userService.deleteUser(id);
//        return ResponseEntity.noContent().build();
//    }
}
