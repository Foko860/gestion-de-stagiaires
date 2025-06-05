package com.laosarl.gestion_de_stagiaires.Service.mapper;

import com.laosarl.gestion_de_stagiaires.Model.User;
import com.laosarl.internship_management.model.UserRequestDTO;
import com.laosarl.internship_management.model.UserResponseDTO;

/**
 * Utility class for converting between User entities and DTOs.
 */
public class UserMapper {

    /**
     * Converts a UserRequestDTO into a User entity.
     *
     * @param userRequestDTO the incoming DTO with user data
     * @return User entity
     */
    public static User fromRequestDTO(UserRequestDTO userRequestDTO) {
        User user = new User();
        user.setUsername(userRequestDTO.getUsername());
        user.setPassword(userRequestDTO.getPassword()); // ⚠️ To be encoded in service
        user.setEmail(userRequestDTO.getEmail());
        user.setFirstName(userRequestDTO.getFirstName());
        user.setLastName(userRequestDTO.getLastName());
        return user;
    }

    /**
     * Converts a User entity into a UserResponseDTO, excluding sensitive data.
     *
     * @param user the user entity
     * @return user response DTO
     */
    public static UserResponseDTO toResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        return dto;
    }

    /**
     * Updates an existing User entity with non-null fields from another User (used during update).
     *
     * @param source the source User containing new data
     * @param target the existing User to be updated
     */
    public static void update(User source, User target) {
        if (source.getUsername() != null) {
            target.setUsername(source.getUsername());
        }
        if (source.getEmail() != null) {
            target.setEmail(source.getEmail());
        }
        if (source.getFirstName() != null) {
            target.setFirstName(source.getFirstName());
        }
        if (source.getLastName() != null) {
            target.setLastName(source.getLastName());
        }
        if (source.getPassword() != null) {
            target.setPassword(source.getPassword());
        }
    }

    /**
     * Removes the password field from a User entity before returning it.
     *
     * @param user the user entity
     * @return user with null password
     */
    public static User removePassword(User user) {
        user.setPassword(null);
        return user;
    }
}
