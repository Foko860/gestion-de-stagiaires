package com.laosarl.gestion_de_stagiaires.Service;

import com.laosarl.gestion_de_stagiaires.Model.User;
import com.laosarl.gestion_de_stagiaires.Repository.UserRepository;
import com.laosarl.gestion_de_stagiaires.Service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;


    @Transactional
    public User createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already in use");
        }
        return userRepository.save(user);
    }

    //TODO: remove the field password
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id).map(UserMapper::removePassword);
    }

    @Transactional
    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));


        verification(userDetails, user);
        UserMapper.update(userDetails, user);

        return userRepository.save(user);
    }

    private void verification(User userDetails, User user) {
        if (userRepository.existsByUsername(userDetails.getUsername())) {
            throw new RuntimeException("Username already taken");
        }

        //TODO: Translate in english
        if (user.getUsername().equals(userDetails.getUsername())) {
            throw new RuntimeException("it is already your username");
        }

        if (!user.getEmail().equals(userDetails.getEmail()) &&
            userRepository.existsByEmail(userDetails.getEmail())) {
            throw new RuntimeException("Email already in use");
        }
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}